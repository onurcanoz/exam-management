package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.StudentRepository;
import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Student;
import com.doping.exammanagement.dto.student.StudentDto;
import com.doping.exammanagement.dto.student.StudentExamDto;
import com.doping.exammanagement.dto.student.request.CreateStudentExamRequest;
import com.doping.exammanagement.dto.student.request.CreateStudentRequest;
import com.doping.exammanagement.dto.student.request.ExamIdRequest;
import com.doping.exammanagement.dto.student.request.UpdateStudentRequest;
import com.doping.exammanagement.exception.InvalidExamException;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final ExamService examService;

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentDto::convert)
                .toList();
    }

    @Cacheable(value = "students", key = "#id")
    public StudentDto getStudentById(long id) {
        return StudentDto.convert(findById(id));
    }

    @CachePut(value = "students", key = "#result.id")
    public StudentDto create(CreateStudentRequest request) {
        if (!isStudentExistsByIdentityNumber(request.getIdentityNumber())) {
            Student student = new Student();
            student.setFirstName(request.getFirstName());
            student.setLastName(request.getLastName());
            student.setIdentityNumber(request.getIdentityNumber());

            return StudentDto.convert(studentRepository.save(student));
        } else {
            throw new ResourceConflictException("Student with the student number " + request.getIdentityNumber() + "' already exists.");
        }

    }

    @CachePut(value = "students", key = "#id")
    public StudentDto updateById(long id, UpdateStudentRequest request) {
        Student student = findById(id);
        if (!isStudentExistsByIdNotAndIdentityNumber(id, request.getIdentityNumber())) {
            student.setFirstName(request.getFirstName());
            student.setLastName(request.getLastName());
            student.setIdentityNumber(request.getIdentityNumber());

            return StudentDto.convert(studentRepository.save(student));
        } else {
            throw new ResourceConflictException("Student with the student number " + request.getIdentityNumber() + "' already exists.");
        }
    }

    @CacheEvict(value = "students", key = "#id")
    public void deleteById(long id) { studentRepository.deleteById(id); }

    @CacheEvict(value = "studentExams", key = "#studentId")
    public void deleteStudentExamByIdAndExamId(long studentId, long examId) {
        Student student = findById(studentId);
        student.getExams().remove(examService.findById(examId));

        studentRepository.save(student);
    }

    public StudentExamDto getStudentExamById(long id) {
        return StudentExamDto.convert(findById(id));
    }

    public StudentExamDto addExamsByStudentId(long id, CreateStudentExamRequest request) {
        Student student = findById(id);

        List<Long> examIds = request.getExams().stream()
                .map(ExamIdRequest::getId)
                .toList();

        List<Exam> exams = examService.findByIdIn(examIds);

        exams.stream()
                .filter(exam -> !exam.isValid())
                .findAny()
                .ifPresent(exam -> {
                    throw new InvalidExamException("Invalid exam detected: " + exam.getId());
                });

        for (ExamIdRequest examId: request.getExams()) {
            Exam exam = exams.stream()
                    .filter(e -> e.getId().equals(examId.getId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Exam could not find by id: " + examId.getId()));

            student.getExams().add(exam);
        }

        return StudentExamDto.convert(studentRepository.save(student));
    }

    protected Student findById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student could not find by id: " + id));
    }

    protected boolean isStudentExistsByIdentityNumber(String identityNumber) {
        return studentRepository.countByIdentityNumber(identityNumber) > 0;
    }

    protected boolean isStudentExistsByIdNotAndIdentityNumber(long id, String identityNumber) {
        return studentRepository.existsByIdNotAndIdentityNumber(id, identityNumber);
    }
}
