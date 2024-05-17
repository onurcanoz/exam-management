package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.ExamRepository;
import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.domain.QuestionItem;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.exam.reqeust.CreateExamRequest;
import com.doping.exammanagement.dto.exam.ExamDto;
import com.doping.exammanagement.dto.exam.reqeust.UpdateExamRequest;
import com.doping.exammanagement.dto.item.request.CreateQuestionItemRequest;
import com.doping.exammanagement.dto.item.request.UpdateQuestionItemRequest;
import com.doping.exammanagement.dto.question.request.CreateQuestionRequest;
import com.doping.exammanagement.dto.question.request.UpdateQuestionRequest;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import com.doping.exammanagement.exception.VerifiedResourceCannotChangeException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionService questionService;
    private final QuestionItemService questionItemService;

    public List<ExamSimpleDto> getAllExams() {
        return examRepository.findAll().stream()
                .map(ExamSimpleDto::simpleDtoConvert)
                .toList();
    }

    @Cacheable(value = "exams", key = "#id")
    public ExamDto getExamById(long id) {
        return ExamDto.convert(examRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Exam could not find by id: " + id)));
    }

    @CachePut(value = "exams", key = "#result.id")
    public ExamDto create(CreateExamRequest request) {
        Exam exam = new Exam();
        exam.setName(request.getName());
        exam.setMaxScore(request.getMaxScore());

        for (CreateQuestionRequest questionRequest: request.getQuestions()) {
            Question question = new Question();
            question.setText(questionRequest.getText());
            question.setPoint(questionRequest.getPoint());

            for (CreateQuestionItemRequest itemRequest: questionRequest.getItems()) {
                QuestionItem questionItem = new QuestionItem();
                questionItem.setTag(itemRequest.getTag());
                questionItem.setText(itemRequest.getText());
                questionItem.setValid(itemRequest.getValid());
                questionItem.setExam(exam);

                question.addItem(questionItem);
            }

            exam.addQuestion(question);
        }

        return ExamDto.convert(examRepository.save(exam));
    }

    @CachePut(value = "exams", key = "#id")
    public ExamDto updateById(long id, UpdateExamRequest request) {
        Exam exam = findById(id);
        if (!exam.isValid()) {
            exam.setName(request.getName());
            exam.setMaxScore(request.getMaxScore());

            if (request.getQuestions() != null) {
                for (UpdateQuestionRequest questionRequest: request.getQuestions()) {
                    for (Question question: exam.getQuestions()) {
                        if (questionRequest.getId().equals(question.getId())) {
                            question.setText(questionRequest.getText() != null ? questionRequest.getText() : question.getText());
                            question.setPoint(questionRequest.getPoint() != null ? questionRequest.getPoint() : question.getPoint());
                            if (questionRequest.getItems() != null) {
                                for (UpdateQuestionItemRequest questionItemRequest: questionRequest.getItems()) {
                                    for (QuestionItem questionItem: question.getItems()) {
                                        if (questionItemRequest.getId().equals(questionItem.getId())) {
                                            questionItem.setTag(questionItemRequest.getTag() != null ? questionItemRequest.getTag() : questionItem.getTag());
                                            questionItem.setText(questionItemRequest.getText() != null ? questionItemRequest.getText() : questionItem.getText() );
                                            questionItem.setValid(questionItemRequest.getValid() != null ? questionItemRequest.getValid() : questionItem.isValid() );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return ExamDto.convert(examRepository.save(exam));
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + exam.getId());
        }

    }

    @CacheEvict(value = "exams", key = "#id")
    public void deleteById(long id) {
        if (!findById(id).isValid()) {
            examRepository.deleteById(id);
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + id);
        }

    }

    @CachePut(value = "exams", key = "#id")
    public ExamDto addQuestions(CreateQuestionRequest request, long id) {
        Exam exam = findById(id);

        if (!exam.isValid()) {
            Question question = new Question();
            question.setText(request.getText());
            question.setPoint(request.getPoint());

            for (CreateQuestionItemRequest questionItemRequest: request.getItems()) {
                QuestionItem item = new QuestionItem();
                item.setTag(questionItemRequest.getTag());
                item.setText(questionItemRequest.getText());
                item.setValid(questionItemRequest.getValid());
                item.setExam(exam);

                question.addItem(item);
            }

            exam.addQuestion(question);

            return ExamDto.convert(examRepository.save(exam));
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + exam.getId());
        }
    }

    @CachePut(value = "exams", key = "#result.id")
    public ExamDto addQuestionItemByExamIdAndQuestionId(long examId, long questionId, List<CreateQuestionItemRequest> request) {
        if (!findById(examId).isValid()) {
            Question question = questionService.findByIdAndExamId(questionId, examId);

            for (CreateQuestionItemRequest item: request) {
                QuestionItem temp = new QuestionItem();
                temp.setTag(item.getTag());
                temp.setText(item.getText());
                temp.setValid(item.getValid());
                temp.setExam(question.getExam());

                question.addItem(temp);
            }
            return ExamDto.convert(questionService.save(question).getExam());
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + examId);
        }
    }

    @CacheEvict(value = "exams", key = "#examId")
    public void deleteQuestionItemByExamIdAndQuestionId(long examId, long questionId, long itemId) {
        if (!findById(examId).isValid()) {
            questionItemService.deleteQuestionItem(itemId, questionId, examId);
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + examId);
        }
    }

    @CachePut(value = "exams", key = "#id")
    public ExamDto verifyById(long id) {
        Exam exam = findById(id);
        if (exam.getQuestions().stream().mapToInt(Question::getPoint).sum() - exam.getMaxScore() == 0) {
            exam.setValid(true);
            examRepository.save(exam);
        }
        return ExamDto.convert(exam);
    }

    @CacheEvict(value = "exams", key = "#examId")
    public void deleteQuestionByExamIdAndQuestionId(long examId, long questionId) {
        if (!findById(examId).isValid()) {
            questionService.deleteQuestionByExamIdAndQuestionId(examId, questionId);
        } else {
            throw new VerifiedResourceCannotChangeException("Verified exam cannot be changed : " + examId);
        }
    }

    protected Exam findById(long id) {
        return examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exam could not find by id: " + id));
    }

    protected List<Exam> findByIdIn(List<Long> examIds) {
        return examRepository.findByIdIn(examIds);
    }
}
