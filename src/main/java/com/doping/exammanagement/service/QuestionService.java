package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.QuestionRepository;
import com.doping.exammanagement.domain.Question;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void deleteQuestionByExamIdAndQuestionId(long examId, long questionId) {
        questionRepository.deleteById(questionId);
    }

    protected Question findByIdAndExamId(long questionId, long examId) {
        return questionRepository.findByIdAndExam_Id(questionId, examId)
                .orElseThrow(() -> new ResourceNotFoundException("Question could not find by id: " + questionId + " and exam id: " + examId));
    }

    protected Question save(Question question) {
        return questionRepository.save(question);
    }
}
