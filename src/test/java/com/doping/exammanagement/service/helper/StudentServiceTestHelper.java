package com.doping.exammanagement.service.helper;

import com.doping.exammanagement.domain.Exam;
import com.doping.exammanagement.domain.Student;
import com.doping.exammanagement.dto.exam.StudentActiveExam;
import com.doping.exammanagement.dto.student.StudentDto;
import com.doping.exammanagement.dto.student.StudentExamDto;
import com.doping.exammanagement.dto.student.request.CreateStudentExamRequest;
import com.doping.exammanagement.dto.student.request.CreateStudentRequest;
import com.doping.exammanagement.dto.student.request.ExamIdRequest;
import com.doping.exammanagement.dto.student.request.UpdateStudentRequest;

public class StudentServiceTestHelper {

    public CreateStudentRequest generateValidCreateStudentRequest() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("Onur Can");
        request.setLastName("Öz");
        request.setIdentityNumber("90106530");

        return request;
    }

    public CreateStudentExamRequest generateCreateStudentExamRequest() {
        CreateStudentExamRequest request = new CreateStudentExamRequest();
        ExamIdRequest examIdRequest = new ExamIdRequest();
        examIdRequest.setId(1L);
        request.getExams().add(examIdRequest);

        return request;
    }

    public CreateStudentExamRequest generateNonCreateStudentNonExistsExamRequest() {
        CreateStudentExamRequest request = new CreateStudentExamRequest();
        ExamIdRequest examIdRequest = new ExamIdRequest();
        examIdRequest.setId(1000L);
        request.getExams().add(examIdRequest);

        return request;
    }

    public UpdateStudentRequest generateUpdateStudentRequest() {
        UpdateStudentRequest request = new UpdateStudentRequest();
        request.setFirstName("Onur Can");
        request.setLastName("Lort");
        request.setIdentityNumber("90106530");

        return request;
    }

    public StudentExamDto generateStudentExamDto() {
        StudentExamDto studentExamDto = new StudentExamDto();
        studentExamDto.setId(1);
        studentExamDto.setFirstName("Onur Can");
        studentExamDto.setLastName("Öz");
        studentExamDto.setIdentityNumber("90106530");

        StudentActiveExam activeExam = new StudentActiveExam();
        activeExam.setId(1);
        activeExam.setName("Matematik Test");
        activeExam.setMaxScore(100);
        activeExam.setActive(true);

        studentExamDto.getExams().add(activeExam);

        return studentExamDto;
    }

    public Student generateStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Onur Can");
        student.setLastName("Öz");
        student.setIdentityNumber("90106530");

        return student;
    }

    public Student generateStudentWithExam() {
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Onur Can");
        student.setLastName("Öz");
        student.setIdentityNumber("90106530");

        Exam exam = new Exam();
        exam.setId(1L);
        exam.setName("Matematik Test");
        exam.setMaxScore(100);
        exam.setValid(true);

        student.getExams().add(exam);

        return student;
    }

    public Exam generateExam() {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setName("Matematik Test");
        exam.setMaxScore(100);
        exam.setValid(true);

        return exam;
    }

    public Exam generateInvalidExam() {
        Exam exam = new Exam();
        exam.setId(2L);
        exam.setName("Matematik Test");
        exam.setMaxScore(100);
        exam.setValid(false);

        return exam;
    }

    public StudentDto generateStudentDto() {
        return StudentDto.builder()
                .id(1L)
                .firstName("Onur Can")
                .lastName("Öz")
                .identityNumber("90106530")
                .build();
    }

    public StudentDto generateUpdatedStudentDto() {
        return StudentDto.builder()
                .id(1L)
                .firstName("Onur Can")
                .lastName("Lort")
                .identityNumber("90106530")
                .build();
    }
}
