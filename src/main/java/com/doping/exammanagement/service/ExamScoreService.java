package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.ExamScoreRepository;
import com.doping.exammanagement.domain.*;
import com.doping.exammanagement.dto.score.ExamScoreDetailDto;
import com.doping.exammanagement.dto.score.ExamScoreDto;
import com.doping.exammanagement.dto.score.request.CreateExamScoreRequest;
import com.doping.exammanagement.dto.score.request.QuestionAnswerRequest;
import com.doping.exammanagement.dto.score.request.UpdateExamScoreRequest;
import com.doping.exammanagement.exception.ResourceConflictException;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamScoreService {

    private final ExamScoreRepository examScoreRepository;
    private final StudentService studentService;

    public List<ExamScoreDto> getAllExamScores() {
        return examScoreRepository.findAll().stream()
                .map(ExamScoreDto::convert)
                .toList();
    }

    @Cacheable(value = "examScoreDetail", key = "{#studentId, #examId}")
    public ExamScoreDetailDto getExamScoresByStudentIdAndExamId(long studentId, long examId) {
        return ExamScoreDetailDto.convert(findByStudentIdAndExamId(studentId, examId));
    }

    @CacheEvict(value = "examScoreDetail", key = "{#studentId, #examId}")
    public void deleteExamScore(long studentId, long examId) {
        ExamScore examScore = findByStudentIdAndExamId(studentId, examId);
        examScoreRepository.deleteById(examScore.getId());
    }

    @CachePut(value = "examScoreDetail", key = "{#request.student.id, #request.exam.id}")
    public ExamScoreDetailDto create(CreateExamScoreRequest request) {
        if (examScoreRepository.findByStudentIdAndExamId(request.getStudent().getId(), request.getExam().getId()).isEmpty()) {
            Student student = studentService.findById(request.getStudent().getId());

            Exam exam = student.getExams().stream()
                    .filter(e -> e.getId().equals(request.getExam().getId()))
                    .filter(Exam::isValid)
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Valid exam could not find by id: " + request.getExam().getId()));

            ExamScore examScore = new ExamScore();
            examScore.setStudent(student);
            examScore.setExam(exam);

            int totalScore = 0;
            for (Question question: exam.getQuestions()) {
                for (QuestionAnswerRequest answer: request.getExam().getAnswers()) {
                    if (question.getId().equals(answer.getQuestionId())) {
                        for (QuestionItem questionItem: question.getItems()) {
                            if (questionItem.getId().equals(answer.getAnswerId())) {
                                if (questionItem.isValid()) {
                                    // correct answer
                                    ExamScoreItem examScoreItem = new ExamScoreItem();
                                    examScoreItem.setExamScore(examScore);
                                    examScoreItem.setQuestion(question);
                                    examScoreItem.setQuestionItem(questionItem);
                                    examScoreItem.setScore(question.getPoint());
                                    examScoreItem.setValid(true);
                                    totalScore += question.getPoint();

                                    examScore.getItems().add(examScoreItem);
                                } else {
                                    // wrong answer
                                    ExamScoreItem examScoreItem = new ExamScoreItem();
                                    examScoreItem.setExamScore(examScore);
                                    examScoreItem.setQuestion(question);
                                    examScoreItem.setQuestionItem(questionItem);
                                    examScoreItem.setScore(0);
                                    examScoreItem.setValid(false);

                                    examScore.getItems().add(examScoreItem);
                                }
                            }
                        }
                    }
                }
            }

            examScore.setScore(totalScore);
            return ExamScoreDetailDto.convert(examScoreRepository.save(examScore));
        } else {
            throw new ResourceConflictException("Exam score with the student id: " + request.getStudent().getId() +
                    " and exam id : " + request.getExam().getId() +  " already exists");
        }

    }

    @CachePut(value = "examScoreDetail", key = "{#request.student.id, #request.exam.id}")
    public ExamScoreDetailDto update(UpdateExamScoreRequest request) {
        ExamScore examScore = findByStudentIdAndExamId(request.getStudent().getId(), request.getExam().getId());
        Exam exam = examScore.getExam();
        examScore.getItems().clear();

        int totalScore = 0;
        for (Question question: exam.getQuestions()) {
            for (QuestionAnswerRequest answer: request.getExam().getAnswers()) {
                if (question.getId().equals(answer.getQuestionId())) {
                    for (QuestionItem questionItem: question.getItems()) {
                        if (questionItem.getId().equals(answer.getAnswerId())) {
                            if (questionItem.isValid()) {
                                // correct answer
                                ExamScoreItem examScoreItem = new ExamScoreItem();
                                examScoreItem.setExamScore(examScore);
                                examScoreItem.setQuestion(question);
                                examScoreItem.setQuestionItem(questionItem);
                                examScoreItem.setScore(question.getPoint());
                                examScoreItem.setValid(true);
                                totalScore += question.getPoint();

                                examScore.getItems().add(examScoreItem);
                            } else {
                                // wrong answer
                                ExamScoreItem examScoreItem = new ExamScoreItem();
                                examScoreItem.setExamScore(examScore);
                                examScoreItem.setQuestion(question);
                                examScoreItem.setQuestionItem(questionItem);
                                examScoreItem.setScore(0);
                                examScoreItem.setValid(false);

                                examScore.getItems().add(examScoreItem);
                            }
                        }
                    }
                }
            }
        }

        examScore.setScore(totalScore);
        return ExamScoreDetailDto.convert(examScoreRepository.save(examScore));
    }

    protected ExamScore findByStudentIdAndExamId(long studentId, long examId) {
        return examScoreRepository.findByStudentIdAndExamId(studentId, examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam score could not find by student id: " + studentId + " and exam id: "+ examId));
    }
}
