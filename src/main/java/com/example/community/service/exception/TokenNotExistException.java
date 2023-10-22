package com.example.community.service.exception;

public class TokenNotExistException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "refresh token이 존재하지 않습니다.";

  public TokenNotExistException() {
    super(DEFAULT_MESSAGE);
  }
}
