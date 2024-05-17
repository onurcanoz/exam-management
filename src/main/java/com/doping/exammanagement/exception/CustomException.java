package com.doping.exammanagement.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CustomException {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("status")
    private int status;

    @JsonProperty("errors")
    List<String> errors;

    public CustomException() {
        this.errors = new ArrayList<>();
        this.timestamp = LocalDateTime.now();

    }
}
