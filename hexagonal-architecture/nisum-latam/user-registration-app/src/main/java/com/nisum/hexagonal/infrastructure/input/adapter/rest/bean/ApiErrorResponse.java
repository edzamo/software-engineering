package com.nisum.hexagonal.infrastructure.input.adapter.rest.bean;


import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ApiErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private List<ApiSubError> errors;

  public ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, List<ApiSubError> errors) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
    this.errors = errors;
  }


}



