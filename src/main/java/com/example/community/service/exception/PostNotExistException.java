package com.example.community.service.exception;

public class PostNotExistException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "게시글이 존재하지 않습니다.";

  public PostNotExistException() {
    super(DEFAULT_MESSAGE);
  }
}
