package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.QuestionItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class QuestionItemService {

    private final QuestionItemRepository questionItemRepository;

    @Transactional
    public void deleteQuestionItem(long id, long questionId, long examId) {
        questionItemRepository.deleteById(id);
    }

}
