package com.doping.exammanagement.controller;

import com.doping.exammanagement.dto.api.ApiSuccessResponse;
import com.doping.exammanagement.dto.student.StudentDto;
import com.doping.exammanagement.dto.student.StudentExamDto;
import com.doping.exammanagement.dto.student.request.CreateStudentExamRequest;
import com.doping.exammanagement.dto.student.request.CreateStudentRequest;
import com.doping.exammanagement.dto.student.request.UpdateStudentRequest;
import com.doping.exammanagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/students")
@AllArgsConstructor
@Tag(name = "students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "get all students", description = "Retrieves a list of all students")
    public ResponseEntity<ApiSuccessResponse<List<StudentDto>>> getAllStudents() {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", studentService.getAllStudents()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get student by id", description = "Retrieves the student with the specified id")
    public ResponseEntity<ApiSuccessResponse<StudentDto>> getStudentById(@PathVariable long id) {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", studentService.getStudentById(id)));
    }

    @PostMapping
    @Operation(summary = "create a new student", description = "Creates a new student based on the provided request")
    public ResponseEntity<ApiSuccessResponse<StudentDto>> create(@Valid @RequestBody CreateStudentRequest request) {
        System.out.println(request);
        return new ResponseEntity<>(ApiSuccessResponse.successfulResponseConverter(201, "success", studentService.create(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update student by id", description = "Updates the student with the specified id based on the provided request")
    public ResponseEntity<ApiSuccessResponse<StudentDto>> updateById(@PathVariable long id, @Valid @RequestBody UpdateStudentRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", studentService.updateById(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete student by id", description = "Deletes the student with the specified id")
    private ResponseEntity<?> deleteById(@PathVariable long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }

    @GetMapping("/{id}/exams")
    @Operation(summary = "get student's exam by student id", description = "Retrieves the student's exam with the specified student id")
    public ResponseEntity<ApiSuccessResponse<StudentExamDto>> getStudentExamByStudentId(@PathVariable long id) {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", studentService.getStudentExamById(id)));
    }

    @PostMapping("/{id}/exams")
    @Operation(summary = "add valid exams to student by student ID", description = "Adds valid exams to the student with the specified student id based on the provided request")
    public ResponseEntity<ApiSuccessResponse<StudentExamDto>> addExamsByStudentId(@PathVariable long id, @Valid @RequestBody CreateStudentExamRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", studentService.addExamsByStudentId(id, request)));
    }

    @DeleteMapping("/{studentId}/exams/{examId}")
    @Operation(summary = "delete student's exam by student id and exam id", description = "Deletes the student's exam with the specified student id and exam id")
    public ResponseEntity<?> deleteStudentExamByIdAndExamId(@PathVariable long studentId, @PathVariable long examId) {
        studentService.deleteStudentExamByIdAndExamId(studentId, examId);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }


}
