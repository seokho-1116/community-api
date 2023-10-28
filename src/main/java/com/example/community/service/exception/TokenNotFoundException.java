package com.example.community.service.exception;

public class TokenNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "refresh token이 존재하지 않습니다.";

  public TokenNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
