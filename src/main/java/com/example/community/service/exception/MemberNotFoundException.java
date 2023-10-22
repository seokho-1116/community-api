package com.example.community.service.exception;

public class MemberNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "회원정보가 존재하지 않습니다.";

  public MemberNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
