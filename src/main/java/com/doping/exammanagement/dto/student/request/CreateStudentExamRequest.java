package com.doping.exammanagement.dto.student.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateStudentExamRequest {

    @Valid
    @Size(min = 1, message = "exams is mandatory")
    @JsonProperty("exams")
    private List<ExamIdRequest> exams = new ArrayList<>();
}
