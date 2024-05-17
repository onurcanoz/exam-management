package com.doping.exammanagement.dto.exam.reqeust;

import com.doping.exammanagement.dto.question.request.UpdateQuestionRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
@ToString
public class UpdateExamRequest {

    @NotNull(message = "name is mandatory")
    @JsonProperty("name")
    private String name;

    @Range(min = 1, message= "maxScore is mandatory")
    @JsonProperty("maxScore")
    private int maxScore;

    @JsonProperty("questions")
    private List<@Valid UpdateQuestionRequest> questions;
}
