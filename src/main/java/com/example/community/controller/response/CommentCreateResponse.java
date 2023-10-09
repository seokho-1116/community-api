package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateResponse {
  private final UUID commentId;

  private CommentCreateResponse(UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentCreateResponse create(UUID publicId) {
    return new CommentCreateResponse(publicId);
  }
}
