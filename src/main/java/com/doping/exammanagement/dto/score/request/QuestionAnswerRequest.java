package com.doping.exammanagement.dto.score.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QuestionAnswerRequest {

    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("answerId")
    private Long answerId;
}
