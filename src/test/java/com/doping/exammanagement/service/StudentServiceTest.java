package com.doping.exammanagement.service;

import com.doping.exammanagement.dao.StudentRepository;
import com.doping.exammanagement.domain.Student;
import com.doping.exammanagement.dto.student.StudentDto;
import com.doping.exammanagement.dto.student.StudentExamDto;
import com.doping.exammanagement.dto.student.request.CreateStudentExamRequest;
import com.doping.exammanagement.dto.student.request.CreateStudentRequest;
import com.doping.exammanagement.dto.student.request.UpdateStudentRequest;
import com.doping.exammanagement.exception.InvalidExamException;
import com.doping.exammanagement.exception.ResourceConflictException;
import com.doping.exammanagement.exception.ResourceNotFoundException;
import com.doping.exammanagement.service.helper.StudentServiceTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class StudentServiceTest extends StudentServiceTestHelper {

    private StudentService studentService;

    private StudentRepository studentRepository;
    private ExamService examService;

    @BeforeEach
    public void setUp() {
        studentRepository = Mockito.mock(StudentRepository.class);
        examService = Mockito.mock(ExamService.class);

        studentService = new StudentService(studentRepository, examService);
    }

    @Test
    public void whenGetStudentByExistsId_itShouldReturnStudentDto() {
        Student student = generateStudent();
        StudentDto studentDto = generateStudentDto();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentDto result = studentService.getStudentById(anyLong());

        assertEquals(result, studentDto);
    }

    @Test
    public void whenGetStudentByNonExistsId_itShouldThrowResourceNotFoundException() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(anyLong()));
    }


    @Test
    public void whenCreateStudentCalledWithValidRequest_itShouldReturnValidStudentDto() {
        CreateStudentRequest createStudentRequest = generateValidCreateStudentRequest();
        Student student = generateStudent();
        StudentDto studentDto = generateStudentDto();

        when(studentRepository.countByIdentityNumber(createStudentRequest.getIdentityNumber())).thenReturn(0);
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        StudentDto result = studentService.create(createStudentRequest);

        assertEquals(result, studentDto);
    }

    @Test
    public void whenCreateStudentCalledWithExistingIdentityNumber_itShouldThrowResourceConflictException() {
        CreateStudentRequest createStudentRequest = generateValidCreateStudentRequest();
        when(studentRepository.countByIdentityNumber(createStudentRequest.getIdentityNumber())).thenReturn(1);

        assertThrows(ResourceConflictException.class, () -> studentService.create(createStudentRequest));
    }

    @Test
    public void whenUpdateExistsStudentCalledWithValidIdAndRequest_itShouldReturnUpdatedStudentDto() {
        UpdateStudentRequest request = generateUpdateStudentRequest();
        Student student = generateStudent();
        StudentDto studentDto = generateUpdatedStudentDto();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.existsByIdNotAndIdentityNumber(anyLong(), anyString())).thenReturn(false);
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        StudentDto result = studentService.updateById(anyLong(), request);

        assertEquals(result, studentDto);
        assertEquals(result.getId() ,studentDto.getId());
        assertEquals(result.getFirstName(), studentDto.getFirstName());
        assertEquals(result.getLastName(), studentDto.getLastName());
        assertEquals(result.getIdentityNumber(), studentDto.getIdentityNumber());
    }

    @Test
    public void whenUpdateStudentCalledWithExistingIdentityNumber_itShouldThrowResourceConflictException() {
        UpdateStudentRequest request = generateUpdateStudentRequest();
        Student student = generateStudent();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.existsByIdNotAndIdentityNumber(anyLong(), anyString())).thenReturn(true);

        assertThrows(ResourceConflictException.class, () -> studentService.updateById(anyLong(), request));
    }

    @Test
    public void whenGetStudentExamCalledWithValidId_itShouldReturnValidStudentExamDto() {
        Student student = generateStudent();
        StudentExamDto studentExamDto = generateStudentExamDto();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        StudentExamDto result = studentService.getStudentExamById(anyLong());

        assertEquals(result, studentExamDto);
    }

    @Test
    public void whenAddExamsByStudentIdCalledWithValidIdAndRequest_itShouldReturnValidStudentExamDto() {
        CreateStudentExamRequest request = generateCreateStudentExamRequest();
        StudentExamDto studentExamDto = generateStudentExamDto();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(generateStudent()));
        when(examService.findByIdIn(anyList())).thenReturn(List.of(generateExam()));
        when(studentRepository.save(any(Student.class))).thenReturn(generateStudentWithExam());

        StudentExamDto result = studentService.addExamsByStudentId(1, request);

        assertEquals(result, studentExamDto);
    }

    @Test
    public void whenAddExamsByStudentIdCalledWithInvalidExam_itShouldThrowInvalidExamException() {
        CreateStudentExamRequest request = generateCreateStudentExamRequest();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(generateStudent()));
        when(examService.findByIdIn(anyList())).thenReturn(List.of(generateInvalidExam()));

        assertThrows(InvalidExamException.class, () -> studentService.addExamsByStudentId(anyLong(), request));
    }

    @Test
    public void whenAddExamsByStudentIdCalledWithNonExistsExam_itShouldThrowResourceNotFoundException() {
        CreateStudentExamRequest request = generateNonCreateStudentNonExistsExamRequest();

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(generateStudent()));
        when(examService.findByIdIn(anyList())).thenReturn(List.of(generateExam()));

        assertThrows(ResourceNotFoundException.class, () -> studentService.addExamsByStudentId(1, request));
    }


}
