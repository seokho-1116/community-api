package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentDeleteResponse {
  private final UUID commentId;

  @JsonCreator
  private CommentDeleteResponse(@JsonProperty("commentId") UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentDeleteResponse create(UUID publicId) {
    return new CommentDeleteResponse(publicId);
  }
}
