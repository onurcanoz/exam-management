package com.doping.exammanagement.dao;

import com.doping.exammanagement.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByIdIn(List<Long> examIds);
}
