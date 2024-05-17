package com.doping.exammanagement.dto.score.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateExamScoreRequest {

    @JsonProperty("student")
    private ExamScoreStudentRequest student;

    @JsonProperty("exam")
    private ExamScoreItemRequest exam;
}
