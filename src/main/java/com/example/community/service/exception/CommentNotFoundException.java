package com.example.community.service.exception;

public class CommentNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "댓글 정보가 존재하지 않습니다.";

  public CommentNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
