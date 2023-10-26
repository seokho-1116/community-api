package com.example.community.service.exception;

public class NotResourceOwnerException extends RuntimeException {
  private NotResourceOwnerException(String message) {
    super(message);
  }

  public static NotResourceOwnerException ofComment() {
    return new NotResourceOwnerException("댓글에 대한 권한이 존재하지 않습니다.");
  }
}
