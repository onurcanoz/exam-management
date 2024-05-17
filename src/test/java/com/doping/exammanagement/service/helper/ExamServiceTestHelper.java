package com.doping.exammanagement.service.helper;

import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.domain.QuestionItem;
import com.doping.exammanagement.dto.exam.ExamDto;
import com.doping.exammanagement.dto.exam.reqeust.CreateExamRequest;
import com.doping.exammanagement.dto.exam.reqeust.UpdateExamRequest;
import com.doping.exammanagement.dto.item.request.CreateQuestionItemRequest;
import com.doping.exammanagement.dto.item.request.UpdateQuestionItemRequest;
import com.doping.exammanagement.dto.question.request.CreateQuestionRequest;
import com.doping.exammanagement.dto.question.request.UpdateQuestionRequest;

import java.time.LocalDateTime;
import java.util.List;

public class ExamServiceTestHelper {

    public CreateQuestionItemRequest generetateCreateQuestionItemRequest() {
        CreateQuestionItemRequest request = new CreateQuestionItemRequest();
        request.setTag("B");
        request.setText("Yanıt 2");
        request.setValid(false);

        return request;
    }

    public CreateQuestionRequest generateQuestionRequest() {
        CreateQuestionRequest request = new CreateQuestionRequest();
        request.setText("Soru 2");
        request.setPoint(100);

        CreateQuestionItemRequest itemRequest = new CreateQuestionItemRequest();
        itemRequest.setText("A");
        itemRequest.setTag("Cevap 1");
        itemRequest.setValid(true);

        request.getItems().add(itemRequest);

        return request;
    }

    public CreateExamRequest generateCreateExamRequest() {
        CreateExamRequest request = new CreateExamRequest();
        request.setName("Matematik");
        request.setMaxScore(100);

        CreateQuestionRequest createQuestionRequest = new CreateQuestionRequest();
        createQuestionRequest.setText("Soru 1");
        createQuestionRequest.setPoint(100);


        CreateQuestionItemRequest itemRequest = new CreateQuestionItemRequest();
        itemRequest.setTag("A");
        itemRequest.setText("Cevap 1");
        itemRequest.setValid(true);

        createQuestionRequest.getItems().add(itemRequest);
        request.getQuestions().add(createQuestionRequest);

        return request;
    }

    public UpdateExamRequest generateUpdateExamRequest() {
        UpdateExamRequest request = new UpdateExamRequest();
        request.setName("Türkçe");
        request.setMaxScore(100);

        UpdateQuestionRequest updateQuestionRequest = new UpdateQuestionRequest();
        updateQuestionRequest.setId(1L);
        updateQuestionRequest.setText("Soru 2");
        updateQuestionRequest.setPoint(50);


        UpdateQuestionItemRequest itemRequest = new UpdateQuestionItemRequest();
        itemRequest.setId(1L);
        itemRequest.setTag("B");
        itemRequest.setText("Cevap 2");
        itemRequest.setValid(true);

        updateQuestionRequest.setItems(List.of(itemRequest));
        request.setQuestions(List.of(updateQuestionRequest));

        return request;
    }

    public Exam generateExam(String name, int maxScore, boolean valid, String questionText, int questionPoint,
                              String itemTag, String itemText, boolean itemValid) {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setName(name);
        exam.setMaxScore(maxScore);
        exam.setValid(valid);
        exam.setCreatedAt(LocalDateTime.now());
        exam.setUpdatedAt(LocalDateTime.now());
        exam.addQuestion(generateQuestion(questionText, questionPoint, itemTag, itemText, itemValid));

        return exam;
    }

    public void generateAddQuestionToExam(Exam exam, Question question) {
        exam.getQuestions().add(question);
    }

    public Question generateQuestion(String text, int point, String itemTag, String itemText, boolean itemValid) {
        Question question = new Question();
        question.setId(1L);
        question.setText(text);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        question.setPoint(point);
        question.getItems().add(generateQuestionItem(itemTag, itemText, itemValid));

        return question;
    }

    public Question generateQuestionWithExam(String text, int point, String itemTag, String itemText, boolean itemValid) {
        Question question = new Question();
        question.setId(1L);
        question.setText(text);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        question.setPoint(point);
        question.getItems().add(generateQuestionItem(itemTag, itemText, itemValid));
        Exam exam = generateExam("Matematik", 100, false, "Soru 1", 50, "A", "Cevap 1", true);
        question.setExam(exam);

        return question;
    }

    public QuestionItem generateQuestionItem(String tag, String text, boolean valid) {
        QuestionItem questionItem = new QuestionItem();
        questionItem.setId(1L);
        questionItem.setTag(tag);
        questionItem.setText(text);
        questionItem.setValid(valid);

        return questionItem;
    }

    public ExamDto generateExamDto(String name, int maxScore, boolean valid, String questionText, int questionPoint,
                                    String itemTag, String itemText, boolean itemValid) {
        return ExamDto.convert(
                generateExam(name, maxScore, valid, questionText, questionPoint, itemTag, itemText, itemValid));
    }

    public ExamDto generateExamDto(Exam exam) {
        return ExamDto.convert(exam);
    }

    public Exam addQuestionItemToQuestion(Exam exam, QuestionItem item) {
        exam.getQuestions().get(0).getItems().add(item);
        return exam;
    }
}
