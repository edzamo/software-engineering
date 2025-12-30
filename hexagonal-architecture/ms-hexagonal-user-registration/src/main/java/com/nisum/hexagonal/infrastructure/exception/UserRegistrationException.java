package com.nisum.hexagonal.infrastructure.exception;

public class UserRegistrationException extends RuntimeException {
  public UserRegistrationException(String message) {
    super(message);
  }
}