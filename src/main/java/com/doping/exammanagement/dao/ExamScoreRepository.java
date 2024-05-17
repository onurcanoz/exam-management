package com.doping.exammanagement.dao;

import com.doping.exammanagement.domain.ExamScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExamScoreRepository extends JpaRepository<ExamScore, Long> {

    @Query("select e from ExamScore e where e.student.id = ?1 and  e.exam.id = ?2")
    Optional<ExamScore> findByStudentIdAndExamId(long studentId, long examId);

}
