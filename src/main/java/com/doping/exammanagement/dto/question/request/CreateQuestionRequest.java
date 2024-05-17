package com.doping.exammanagement.dto.question.request;

import com.doping.exammanagement.dto.item.request.CreateQuestionItemRequest;
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
public class CreateQuestionRequest {

    @NotNull(message = "text is mandatory")
    @JsonProperty("text")
    private String text;

    @Range(min = 1, message= "point is mandatory")
    @JsonProperty("point")
    private int point;

    @Valid
    @Size(min = 1, message = "items is mandatory")
    @JsonProperty("items")
    private List<CreateQuestionItemRequest> items = new ArrayList<>();
}
