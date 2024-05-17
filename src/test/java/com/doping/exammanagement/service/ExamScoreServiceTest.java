package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.ExamScoreRepository;
import com.doping.exammanagement.domain.*;
import com.doping.exammanagement.dto.score.ExamScoreDetailDto;
import com.doping.exammanagement.dto.score.request.*;
import com.doping.exammanagement.exception.ResourceConflictException;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import com.doping.exammanagement.service.helper.ExamScoreServiceTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ExamScoreServiceTest extends ExamScoreServiceTestHelper {

    private ExamScoreService examScoreService;

    private ExamScoreRepository examScoreRepository;
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        examScoreRepository = Mockito.mock(ExamScoreRepository.class);
        studentService = Mockito.mock(StudentService.class);

        examScoreService = new ExamScoreService(examScoreRepository, studentService);
    }

    @Test
    public void whenGetExamScoresByStudentIdAndExamId_itShouldReturnExamScoreDetailDto() {
        ExamScore examScore = generateExamScore(true);
        ExamScoreDetailDto examScoreDetailDto = generateExamScoreDetailDto(true);

        when(examScoreRepository.findByStudentIdAndExamId(anyLong(), anyLong())).thenReturn(Optional.of(examScore));

        ExamScoreDetailDto result = examScoreService.getExamScoresByStudentIdAndExamId(1, 1);

        assertEquals(result, examScoreDetailDto);
    }

    @Test
    public void whenCreateExamScoreCalledWithValidRequest_itShouldReturnExamScoreDetailDto() {
        CreateExamScoreRequest request = generateCreateExamScoreRequest();
        Student student = generateStudent(true);
        ExamScoreDetailDto examScoreDetailDto = generateExamScoreDetailDto(true);
        ExamScore examScore = generateExamScore(true);

        when(examScoreRepository.findByStudentIdAndExamId(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(studentService.findById(anyLong())).thenReturn(student);
        when(examScoreRepository.save(Mockito.any(ExamScore.class))).thenReturn(examScore);

        ExamScoreDetailDto result = examScoreService.create(request);

        assertNotNull(result);
        assertEquals(result.getId(), examScoreDetailDto.getId());
        assertEquals(result.getScore(), examScoreDetailDto.getScore());
    }

    @Test
    public void whenCreateExamCalledWithExistsExamScoreRequest_itShouldThrowResourceConflictException() {
        CreateExamScoreRequest request = generateCreateExamScoreRequest();
        ExamScore examScore = generateExamScore(true);

        when(examScoreRepository.findByStudentIdAndExamId(anyLong(), anyLong())).thenReturn(Optional.ofNullable(examScore));

        assertThrows(ResourceConflictException.class, () -> examScoreService.create(request));
    }

    @Test
    public void whenCreateExamScoreCalledWithNotValidExam_itShouldThrowResourceNotFoundException() {
        CreateExamScoreRequest request = generateCreateExamScoreRequest();
        Student student = generateStudent(false);

        when(studentService.findById(anyLong())).thenReturn(student);

        assertThrows(ResourceNotFoundException.class, () -> examScoreService.create(request));
    }

    @Test
    public void whenUpdateExamScoreCalledWithValidRequest_itShouldReturnExamScoreDetailDto() {
        UpdateExamScoreRequest request = generateUpdateExamScoreRequest();
        ExamScore examScore = generateExamScore(true);
        ExamScoreDetailDto examScoreDetailDto = generateExamScoreDetailDto(true);

        when(examScoreRepository.findByStudentIdAndExamId(anyLong(), anyLong())).thenReturn(Optional.of(examScore));
        when(examScoreRepository.save(Mockito.any(ExamScore.class))).thenReturn(generateUpdatedExamScore());

        ExamScoreDetailDto result = examScoreService.update(request);

        assertNotNull(result);
        assertEquals(result, examScoreDetailDto);
    }

    @Test
    public void whenUpdateExamScoreCalledWithNonExistsExamScore_itShouldThrowResourceNotFoundException() {
        UpdateExamScoreRequest request = generateUpdateExamScoreRequest();

        when(examScoreRepository.findByStudentIdAndExamId(anyLong(), anyLong())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> examScoreService.update(request));
    }

}
