package com.nisum.hexagonal.infrastructure.input.adapter.rest.exception;

import com.nisum.hexagonal.infrastructure.input.adapter.rest.bean.ApiErrorResponse;
import com.nisum.hexagonal.infrastructure.input.adapter.rest.bean.ApiSubError;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    List<ApiSubError> errors = ex.getBindingResult().getAllErrors().stream()
        .map(error -> {
          String fieldName = ((FieldError) error).getField();
          String message = error.getDefaultMessage();
          Object rejectedValue = ((FieldError) error).getRejectedValue();
          return new ApiSubError(fieldName, rejectedValue, message);
        })
        .toList();

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        "Validation failed for object='" + ex.getObjectName() + "'. Error count: " + errors.size(),
        errors
    );

    return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        ex.getMessage(),
        null
    );

    return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}