package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentDeleteResponse {
  private final UUID commentId;

  private CommentDeleteResponse(UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentDeleteResponse create(UUID publicId) {
    return new CommentDeleteResponse(publicId);
  }
}
