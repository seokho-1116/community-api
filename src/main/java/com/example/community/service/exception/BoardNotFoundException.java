package com.example.community.service.exception;

public class BoardNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "게시판이 존재하지 않습니다.";

  public BoardNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
