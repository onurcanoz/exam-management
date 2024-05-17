package com.doping.exammanagement.dto.exam.reqeust;

import com.doping.exammanagement.dto.question.request.CreateQuestionRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateExamRequest {

    @NotNull(message = "name is mandatory")
    @JsonProperty("name")
    private String name;

    @Range(min = 1, message= "maxScore is mandatory")
    @JsonProperty("maxScore")
    private int maxScore;

    @Valid
    @Size(min = 1, message = "questions is mandatory")
    @JsonProperty("questions")
    private List<CreateQuestionRequest> questions = new ArrayList<>();
}
