package com.doping.exammanagement.dto.item.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateQuestionItemRequest {

    @NotNull(message = "id is mandatory")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("text")
    private String text;

    @JsonProperty("isValid")
    private Boolean valid;

}
