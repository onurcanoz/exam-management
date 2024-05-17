package com.doping.exammanagement.dao;

import com.doping.exammanagement.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    int countByIdentityNumber(String identityNumber);

    boolean existsByIdNotAndIdentityNumber(long id, String identityNumber);

    @Query("select s from Student s join Exam e where s.id = ?1 and e.id = ?2")
    Optional<Student> findByStudentIdAndExamId(long studentId, long examId);
}
