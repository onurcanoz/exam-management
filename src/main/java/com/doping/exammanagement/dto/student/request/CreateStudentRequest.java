package com.doping.exammanagement.dto.student.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateStudentRequest {

    @NotNull(message = "firstName is mandatory")
    @JsonProperty("firstName")
    private String firstName;

    @NotNull(message = "lastName is mandatory")
    @JsonProperty("lastName")
    private String lastName;

    @NotNull(message = "identityNumber is mandatory")
    @JsonProperty("identityNumber")
    private String identityNumber;
}
