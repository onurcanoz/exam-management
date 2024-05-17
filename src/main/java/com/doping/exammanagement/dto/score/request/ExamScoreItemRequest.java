package com.doping.exammanagement.dto.score.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ExamScoreItemRequest {

    @NotNull(message = "id is mandatory")
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "answers is mandatory")
    @JsonProperty("answers")
    private List<QuestionAnswerRequest> answers;


}
