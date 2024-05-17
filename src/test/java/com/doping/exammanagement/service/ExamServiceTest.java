package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.ExamRepository;
import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.dto.exam.ExamDto;
import com.doping.exammanagement.dto.exam.reqeust.CreateExamRequest;
import com.doping.exammanagement.dto.exam.reqeust.UpdateExamRequest;
import com.doping.exammanagement.dto.item.request.CreateQuestionItemRequest;
import com.doping.exammanagement.dto.question.request.CreateQuestionRequest;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import com.doping.exammanagement.exception.VerifiedResourceCannotChangeException;
import com.doping.exammanagement.service.helper.ExamServiceTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ExamServiceTest extends ExamServiceTestHelper {

    private ExamService examService;

    private ExamRepository examRepository;
    private QuestionService questionService;

    @BeforeEach
    public void setUp() {
        examRepository = Mockito.mock(ExamRepository.class);
        questionService = Mockito.mock(QuestionService.class);
        QuestionItemService questionItemService = Mockito.mock(QuestionItemService.class);

        examService = new ExamService(examRepository, questionService, questionItemService);
    }

    @Test
    public void whenGetExamByExistsExamId_itShouldReturnExamDto() {
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        ExamDto examDto = generateExamDto("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        ExamDto result = examService.getExamById(1);

        assertEquals(result, examDto);
    }

    @Test
    public void whenCreateExamCalledWithValidRequest_itShouldReturnExamDto() {
        CreateExamRequest request = new CreateExamRequest();
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        ExamDto examDto = generateExamDto("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);

        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        ExamDto result = examService.create(request);

        assertEquals(result, examDto);
    }

    @Test
    public void whenUpdateExamCalledWithValidRequest_isShouldReturnExamDto() {
        UpdateExamRequest request = generateUpdateExamRequest();
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        ExamDto examDto = generateExamDto("Türkçe", 50, false, "Soru 2", 50, "B", "Cevap 2", false);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
        when(examRepository.save(any(Exam.class)))
                .thenReturn(
                        generateExam("Türkçe", 50, false, "Soru 2", 50, "B", "Cevap 2", false));

        ExamDto result = examService.updateById(anyLong(), request);

        assertEquals(result, examDto);
        assertEquals(result.getName(), examDto.getName());
        assertEquals(result.getMaxScore(), examDto.getMaxScore());
        assertEquals(result.getQuestions().get(0).getText(), examDto.getQuestions().get(0).getText());
        assertEquals(result.getQuestions().get(0).getPoint(), examDto.getQuestions().get(0).getPoint());
        assertEquals(result.getQuestions().get(0).getItems().get(0).getTag(), examDto.getQuestions().get(0).getItems().get(0).getTag());
        assertEquals(result.getQuestions().get(0).getItems().get(0).getText(), examDto.getQuestions().get(0).getItems().get(0).getText());
        assertEquals(result.getQuestions().get(0).getItems().get(0).isValid(), examDto.getQuestions().get(0).getItems().get(0).isValid());

    }

    @Test
    public void whenUpdateExamCalledWithNonExistsExam_itShouldThrowResourceNotFoundException() {
        UpdateExamRequest request = generateUpdateExamRequest();
        when(examRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> examService.updateById(anyLong(), request));
    }

    @Test
    public void whenUpdateVerifiedExamCalledWithValidRequest_itShouldThrowVerifiedResourceCannotChangeException() {
        UpdateExamRequest request = generateUpdateExamRequest();
        Exam exam = generateExam("Matematik", 100, true, "Soru 1", 100, "A", "Cevap 1", true);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        assertThrows(VerifiedResourceCannotChangeException.class, () -> examService.updateById(anyLong(), request));
    }

    @Test
    public void whenAddQuestionCalledWithValidRequest_itShouldReturnExamDto() {
        CreateQuestionRequest request = generateQuestionRequest();
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        Exam updatedExam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        generateAddQuestionToExam(updatedExam, generateQuestion("Soru 2", 100, "A", "Cevap 1", true));
        ExamDto examDto = generateExamDto(updatedExam);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
        when(examRepository.save(any(Exam.class))).thenReturn(updatedExam);

        ExamDto result = examService.addQuestions(request, anyLong());

        assertEquals(result.getQuestions().size(), 2);
        assertEquals(result.getQuestions().get(1).getText(), "Soru 2");
        assertEquals(result.getQuestions().get(1).getPoint(), 100);
        assertEquals(result.getQuestions().get(1).getItems().get(0).getTag(), "A");
        assertEquals(result.getQuestions().get(1).getItems().get(0).getText(), "Cevap 1");
        assertTrue(result.getQuestions().get(1).getItems().get(0).isValid());
    }

    @Test
    public void whenAddQuestionToVerifiedExam_itShouldThrowVerifiedResourceCannotChangeException() {
        CreateQuestionRequest request = generateQuestionRequest();
        Exam exam = generateExam("Matematik", 100, true, "Soru 1", 100, "A", "Cevap 1", true);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        assertThrows(VerifiedResourceCannotChangeException.class, () -> examService.addQuestions(request, anyLong()));
    }

    @Test
    public void whenVerifyExamCalledWithValidExam_itShouldReturnExamDtoValidParamIsTrue() {
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 100, "A", "Cevap 1", true);
        ExamDto examDto = generateExamDto(exam);
        examDto.setValid(true);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        ExamDto result = examService.verifyById(anyLong());

        assertTrue(result.isValid());
    }

    @Test
    public void whenVerifyExamCalledWithInvalidExam_itShouldReturnExamDtoValidParamIsFalse() {
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 50, "A", "Cevap 1", true);
        ExamDto examDto = generateExamDto(exam);

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));

        ExamDto result = examService.verifyById(anyLong());

        assertFalse(result.isValid());
    }

    @Test
    public void whenAddQuestionItemByExamIdAndQuestionIdCalledWithValidRequest_itShouldReturnExamDto() {
        CreateQuestionItemRequest request = generetateCreateQuestionItemRequest();

        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 50, "A", "Cevap 1", true);

        Question question = generateQuestion("Soru 1", 100, "A", "Soru 1", true);

        Question newQuestion = generateQuestion("Soru 1", 100, "A", "Soru 1", true);
        newQuestion.setExam(exam);

        Exam newExam = addQuestionItemToQuestion(exam, generateQuestionItem(request.getTag(), request.getText(), request.getValid()));

        when(examRepository.findById(anyLong())).thenReturn(Optional.of(exam));
        when(questionService.findByIdAndExamId(anyLong(), anyLong())).thenReturn(question);
        when(questionService.save(any(Question.class))).thenReturn(newQuestion);

        ExamDto result = examService.addQuestionItemByExamIdAndQuestionId(1, 1, List.of(request));

        assertEquals(result.getQuestions().get(0).getItems().size(), 2);
    }


}
