package com.doping.exammanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidExamException extends RuntimeException {

    public InvalidExamException(String message) {
        super(message);
    }
}
