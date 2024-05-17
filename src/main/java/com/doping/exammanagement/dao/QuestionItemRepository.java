package com.doping.exammanagement.dao;

import com.doping.exammanagement.domain.QuestionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionItemRepository extends JpaRepository<QuestionItem, Long> {

    @Query("select q from QuestionItem q where q.id = ?1 and q.question.id = ?2 and q.exam.id = ?3")
    Optional<QuestionItem> findByIdAndQuestionIdAndExamId(long id, long questionId, long examId);

    @Modifying
    @Query("delete from QuestionItem q where q.id = ?1 and q.question.id = ?2 and q.exam.id = ?3")
    void deleteByIdAndQuestionIdAndExamId(long id, long questionId, long examId);
}
