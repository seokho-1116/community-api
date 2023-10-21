package com.example.community.service.exception;

public class CommunityNotFoundException extends RuntimeException {
  private static final String DEFAULT_MESSAGE = "커뮤니티 정보가 존재하지 않습니다.";

  public CommunityNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
