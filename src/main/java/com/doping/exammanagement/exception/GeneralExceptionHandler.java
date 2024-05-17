package com.doping.exammanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.getErrors().add(ex.getMessage());

        return new ResponseEntity<>(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.NOT_FOUND.value());
        exception.getErrors().add(ex.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(status.value());
        exception.setErrors(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(exception, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.getErrors().add(ex.getParameterName() + " parameter is missing");

        return new ResponseEntity<>(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.getErrors().add(ex.getPropertyName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName());

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        exception.getErrors().add("Method '" + ex.getMethod() + "' is not supported");

        return new ResponseEntity<>(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        exception.getErrors().add(ex.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.NOT_FOUND.value());
        exception.getErrors().add(e.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<?> resourceConflictExceptionHandler(ResourceConflictException e) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.CONFLICT.value());
        exception.getErrors().add(e.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidExamException.class)
    public ResponseEntity<?> invalidExamExceptionHandler(InvalidExamException e) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.getErrors().add(e.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerifiedResourceCannotChangeException.class)
    public ResponseEntity<?> verifiedResourceCannotChangeExceptionHandler(VerifiedResourceCannotChangeException e) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.BAD_REQUEST.value());
        exception.getErrors().add(e.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        CustomException exception = new CustomException();
        exception.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.getErrors().add(ex.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
