package com.doping.exammanagement.dto.item.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateQuestionItemRequest {

    @NotNull(message = "tag is mandatory")
    @JsonProperty("tag")
    private String tag;

    @NotNull(message = "text is mandatory")
    @JsonProperty("text")
    private String text;

    @NotNull(message = "isValid is mandatory")
    @JsonProperty("isValid")
    private Boolean valid;
}
