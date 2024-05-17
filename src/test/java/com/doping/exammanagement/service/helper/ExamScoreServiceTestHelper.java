package com.doping.exammanagement.service.helper;

import com.doping.exammanagement.domain.*;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.score.ExamScoreDetailDto;
import com.doping.exammanagement.dto.score.ExamScoreItemDto;
import com.doping.exammanagement.dto.score.request.*;
import com.doping.exammanagement.dto.student.StudentDto;

import java.util.List;

public class ExamScoreServiceTestHelper {

    public UpdateExamScoreRequest generateUpdateExamScoreRequest() {
        UpdateExamScoreRequest request = new UpdateExamScoreRequest();

        ExamScoreStudentRequest student = new ExamScoreStudentRequest();
        student.setId(1L);
        student.setIdentityNumber("90106530");

        ExamScoreItemRequest exam = new ExamScoreItemRequest();
        exam.setId(1L);

        QuestionAnswerRequest answers = new QuestionAnswerRequest();
        answers.setQuestionId(1L);
        answers.setAnswerId(1L);

        exam.setAnswers(List.of(answers));

        request.setStudent(student);
        request.setExam(exam);

        return request;
    }

    public CreateExamScoreRequest generateCreateExamScoreRequest() {
        CreateExamScoreRequest request = new CreateExamScoreRequest();

        ExamScoreStudentRequest student = new ExamScoreStudentRequest();
        student.setId(1L);
        student.setIdentityNumber("90106530");

        ExamScoreItemRequest exam = new ExamScoreItemRequest();
        exam.setId(1L);

        QuestionAnswerRequest answers = new QuestionAnswerRequest();
        answers.setQuestionId(1L);
        answers.setAnswerId(1L);

        exam.setAnswers(List.of(answers));

        request.setStudent(student);
        request.setExam(exam);

        return request;
    }

    public ExamScoreDetailDto generateExamScoreDetailDto(boolean isExamValid) {
        ExamScoreDetailDto dto = new ExamScoreDetailDto();
        dto.setId(1);
        dto.setScore(100);
        dto.setExam(ExamSimpleDto.convert(generateExam(isExamValid)));
        dto.setStudent(generateStudentDto());
        dto.getAnswers().add(ExamScoreItemDto.convert(generateExamScoreItem()));

        return dto;
    }

    public ExamScore generateExamScore(boolean isExamValid) {
        ExamScore examScore = new ExamScore();
        examScore.setId(1L);
        examScore.setScore(100);
        examScore.setStudent(generateStudent(isExamValid));
        examScore.setExam(generateExam(isExamValid));
        examScore.getItems().add(generateExamScoreItem());

        return examScore;
    }

    public ExamScore generateUpdatedExamScore() {
        ExamScore examScore = new ExamScore();
        examScore.setId(1L);
        examScore.setScore(100);
        examScore.setStudent(generateStudent(true));
        examScore.setExam(generateExam(true));
        examScore.getItems().add(generateExamScoreItem());

        return examScore;
    }

    public ExamScoreItem generateExamScoreItem() {
        ExamScoreItem examScoreItem = new ExamScoreItem();
        examScoreItem.setId(1L);
        examScoreItem.setScore(100);
        examScoreItem.setValid(true);
        examScoreItem.setQuestion(generateQuestion());
        examScoreItem.setQuestionItem(generateQuestionItem());

        return examScoreItem;
    }

    public Question generateQuestion() {
        Question question = new Question();
        question.setId(1L);
        question.setPoint(100);
        question.getItems().add(generateQuestionItem());

        return question;
    }

    public QuestionItem generateQuestionItem() {
        QuestionItem questionItem = new QuestionItem();
        questionItem.setId(1L);
        questionItem.setValid(true);

        return questionItem;
    }

    public Exam generateExam(boolean valid) {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setName("Matematik");
        exam.setMaxScore(100);
        exam.setValid(valid);
        exam.setQuestions(List.of(generateQuestion()));

        return exam;
    }

    public Student generateStudent(boolean isExamValid) {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Onur Can");
        student.setLastName("Öz");
        student.setIdentityNumber("90106530");
        student.getExams().add(generateExam(isExamValid));


        return student;
    }

    public ExamSimpleDto generateExamSimpleDto() {
        ExamSimpleDto examSimpleDto = new ExamSimpleDto();
        examSimpleDto.setId(1);
        examSimpleDto.setName("Matematik");
        examSimpleDto.setMaxScore(100);
        examSimpleDto.setValid(true);

        return examSimpleDto;
    }

    public StudentDto generateStudentDto() {
        return StudentDto.builder()
                .id(1L)
                .firstName("Onur Can")
                .lastName("Öz")
                .identityNumber("90106530")
                .build();
    }
}
