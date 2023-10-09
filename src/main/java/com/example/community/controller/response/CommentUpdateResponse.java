package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateResponse {
  private final UUID commentId;

  private CommentUpdateResponse(UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentUpdateResponse create(UUID publicId) {
    return new CommentUpdateResponse(publicId);
  }
}
