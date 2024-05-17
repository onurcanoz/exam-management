package com.doping.exammanagement.controller;

import com.doping.exammanagement.dto.api.ApiSuccessResponse;
import com.doping.exammanagement.dto.score.ExamScoreDetailDto;
import com.doping.exammanagement.dto.score.ExamScoreDto;
import com.doping.exammanagement.dto.score.request.CreateExamScoreRequest;
import com.doping.exammanagement.dto.score.request.UpdateExamScoreRequest;
import com.doping.exammanagement.service.ExamScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/exam-scores")
@AllArgsConstructor
@Tag(name = "exam-scores")
public class ExamScoreController {

    private final ExamScoreService examScoreService;

    @GetMapping
    @Operation(summary = "get all exam scores", description = "Retrieves a list of all exam scores")
    public ResponseEntity<ApiSuccessResponse<List<ExamScoreDto>>> getAllExamScores() {
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examScoreService.getAllExamScores()));
    }

    @PostMapping
    @Operation(summary = "create an exam score", description = "Creates a new exam score based on the provided request")
    public ResponseEntity<ApiSuccessResponse<ExamScoreDetailDto>> create(@Valid @RequestBody CreateExamScoreRequest request) {
        System.out.println(request);
        return new ResponseEntity<>(ApiSuccessResponse.successfulResponseConverter(201, "success", examScoreService.create(request)), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "update an exam score", description = "Updates an existing exam score based on the provided request")
    public ResponseEntity<ApiSuccessResponse<ExamScoreDetailDto>> updateExamScore(@Valid @RequestBody UpdateExamScoreRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examScoreService.update(request)));
    }

    @GetMapping("/students/{studentId}/exams/{examId}")
    @Operation(summary = "get exam score by student id and exam id", description = "Retrieves the exam score with the specified student id and exam id")
    public ResponseEntity<ApiSuccessResponse<ExamScoreDetailDto>> getExamScores(@PathVariable long studentId, @PathVariable long examId) {
        System.out.println("StudentId: " + studentId);
        System.out.println("Exam Id: " + examId);

        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", examScoreService.getExamScoresByStudentIdAndExamId(studentId, examId)));
    }

    @DeleteMapping("/students/{studentId}/exams/{examId}")
    @Operation(summary = "delete exam score by student id and exam id", description = "Deletes the exam score with the specified student id and exam id")
    public ResponseEntity<?> deleteExamScore(@PathVariable long examId, @PathVariable long studentId) {
        examScoreService.deleteExamScore(studentId, examId);
        return ResponseEntity.ok(ApiSuccessResponse.successfulResponseConverter(200, "success", null));
    }

}
