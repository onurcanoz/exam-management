package com.doping.exammanagement.dto.student.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExamIdRequest {

    @NotNull(message = "id is mandatory")
    @JsonProperty("id")
    private Long id;
}
