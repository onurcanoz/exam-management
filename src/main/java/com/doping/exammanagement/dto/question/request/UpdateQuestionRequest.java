package com.doping.exammanagement.dto.question.request;

import com.doping.exammanagement.dto.item.request.UpdateQuestionItemRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Valid
public class UpdateQuestionRequest {

    @NotNull(message = "id is mandatory")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("text")
    private String text;

    @JsonProperty("point")
    private Integer point;

    @JsonProperty("items")
    private List<@Valid UpdateQuestionItemRequest> items;
}
