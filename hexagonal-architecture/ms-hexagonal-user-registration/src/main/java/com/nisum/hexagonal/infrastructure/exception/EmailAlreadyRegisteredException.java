package com.nisum.hexagonal.infrastructure.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }
}
