package com.example.community.service.exception;

public class PostNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "게시글이 존재하지 않습니다.";

  public PostNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
