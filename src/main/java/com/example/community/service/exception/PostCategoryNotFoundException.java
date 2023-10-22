package com.example.community.service.exception;

public class PostCategoryNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "게시글 카테고리가 존재하지 않습니다.";

  public PostCategoryNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
