package com.doping.exammanagement.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiSuccessResponse<T>  {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public static <T> ApiSuccessResponse<T> successfulResponseConverter(int status, String message, T data) {
        ApiSuccessResponse<T> response = new ApiSuccessResponse<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
