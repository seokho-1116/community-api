package com.example.community.security.authentication.jwt.exception;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(final String message) {
    super(message);
  }
}