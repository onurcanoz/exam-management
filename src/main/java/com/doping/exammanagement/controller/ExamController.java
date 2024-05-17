package com.doping.exammanagement.controller;

import com.doping.exammanagement.dto.api.ApiSuccessResponse;
import com.doping.exammanagement.dto.exam.ExamSimpleDto;
import com.doping.exammanagement.dto.exam.reqeust.CreateExamRequest;
import com.doping.exammanagement.dto.exam.ExamDto;
import com.doping.exammanagement.dto.exam.reqeust.UpdateExamRequest;
import com.doping.exammanagement.dto.item.request.CreateQuestionItemRequest;
import com.doping.exammanagement.dto.question.request.CreateQuestionRequest;
import com.doping.exammanagement.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/exams")
@AllArgsConstructor
@Tag(name = "exams")
public class ExamController {

    private final ExamService examService;

    @GetMapping
    @Operation(summary = "get all exams", description = "Retrieves a list of all exams available")
    public ResponseEntity<ApiSuccessResponse<List<ExamSimpleDto>>> getAllExams() {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examService.getAllExams()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get exam by id", description = "Retrieves the exam with the specified id")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> getExamById(@PathVariable long id) {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examService.getExamById(id)));
    }

    @PostMapping
    @Operation(summary = "create a new exam", description = "Creates a new exam based on the provided request")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> create(@Valid @RequestBody CreateExamRequest request) {
        System.out.println(request);
        return new ResponseEntity<>(ApiSuccessResponse.successfulResponseConverter(201, "success", examService.create(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update exam by id", description = "Updates the exam with the specified id based on the provided request")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> updateById(@PathVariable long id, @Valid @RequestBody UpdateExamRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examService.updateById(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete exam by id", description = "Deletes the exam with the specified id")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        examService.deleteById(id);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }

    @PostMapping("/{id}/questions")
    @Operation(summary = "add questions to exam by id", description = "Adds questions to the exam with the specified id based on the provided request")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> addQuestions(@Valid @RequestBody CreateQuestionRequest request, @PathVariable long id) {
        System.out.println(request);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examService.addQuestions(request, id)));
    }

    @DeleteMapping("/{examId}/questions/{questionId}")
    @Operation(summary = "delete question from exam by exam id and question id", description = "Deletes the question with the specified id from the exam with the specified id")
    public ResponseEntity<?> deleteQuestionById(@PathVariable long examId, @PathVariable long questionId) {
        examService.deleteQuestionByExamIdAndQuestionId(examId, questionId);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }

    @PostMapping("/{examId}/questions/{questionId}/items")
    @Operation(summary = "add items to question of exam by exam id and question id", description = "Adds items to the question with the specified ID from the exam with the specified ID based on the provided requests")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> addQuestionItems(@PathVariable long examId, @PathVariable long questionId,
                                                        @RequestBody List<CreateQuestionItemRequest> requests) {
        System.out.println(requests);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success",
                examService.addQuestionItemByExamIdAndQuestionId(examId, questionId, requests)));

    }

    @DeleteMapping("/{examId}/questions/{questionId}/items/{itemId}")
    @Operation(summary = "delete item from question of exam by exam id, question id, and item id", description = "Deletes the item with the specified id from the question with the specified id from the exam with the specified id")
    public ResponseEntity<?> deleteQuestionItemByExamIdAndQuestionId(@PathVariable long examId, @PathVariable long questionId, @PathVariable long itemId) {
        examService.deleteQuestionItemByExamIdAndQuestionId(examId, questionId, itemId);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }

    @GetMapping("/{id}/verify")
    @Operation(summary = "verify an exam", description = "Verify an exam based on the equality of the total score of the exam and the sum of the scores of its questions")
    public ResponseEntity<ApiSuccessResponse<ExamDto>> verifyExam(@PathVariable long id) {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examService.verifyById(id)));
    }

}
