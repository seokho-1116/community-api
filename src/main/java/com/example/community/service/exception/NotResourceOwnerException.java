package com.example.community.service.exception;

public class NotResourceOwnerException extends RuntimeException {
  private NotResourceOwnerException(String message) {
    super(message);
  }

  public static NotResourceOwnerException ofComment() {
    return new NotResourceOwnerException("댓글에 대한 권한이 존재하지 않습니다.");
  }

  public static NotResourceOwnerException ofPost() {
    return new NotResourceOwnerException("게시글에 대한 권한이 존재하지 않습니다.");
  }

  public static NotResourceOwnerException ofMember() {
    return new NotResourceOwnerException("해당 멤버에 대한 권한이 존재하지 않습니다.");
  }
}
