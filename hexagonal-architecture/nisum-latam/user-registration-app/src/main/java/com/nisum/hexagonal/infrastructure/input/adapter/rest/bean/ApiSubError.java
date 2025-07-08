package com.nisum.hexagonal.infrastructure.input.adapter.rest.bean;

import lombok.Data;

@Data
public class ApiSubError {

  private String field;
  private Object rejectedValue;
  private String message;

  public ApiSubError(String field, Object rejectedValue, String message) {
    this.field = field;
    this.rejectedValue = rejectedValue;
    this.message = message;
  }
}
