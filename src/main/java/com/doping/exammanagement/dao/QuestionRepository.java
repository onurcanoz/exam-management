package com.doping.exammanagement.dao;

import com.doping.exammanagement.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByExam_Id(long id);

    Optional<Question> findByIdAndExam_Id(long questionId, long examId);

    @Modifying
    @Query("delete from Question q where q.exam.id = ?1 and q.id = ?2")
    void deleteByExam_IdAndId(long examId, long id);
}
