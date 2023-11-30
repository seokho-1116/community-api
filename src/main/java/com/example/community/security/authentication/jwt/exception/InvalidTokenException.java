package com.example.community.security.authentication.jwt.exception;

public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(final String message) {
    super(message);
  }

  public static InvalidTokenException ofJwt() {
    return new InvalidTokenException("jwt 토큰이 유효하지 않습니다.");
  }

  public static InvalidTokenException ofRefresh() {
    return new InvalidTokenException("refresh 토큰이 올바르지 않거나 권한이 없습니다.");
  }
}