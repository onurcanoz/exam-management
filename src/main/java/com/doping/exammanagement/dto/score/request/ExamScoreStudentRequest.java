package com.doping.exammanagement.dto.score.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamScoreStudentRequest {

    @NotNull(message = "id is mandatory")
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "identityNumber is mandatory")
    @JsonProperty("identityNumber")
    private String identityNumber;
}
