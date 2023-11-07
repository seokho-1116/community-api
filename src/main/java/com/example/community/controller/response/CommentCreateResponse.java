package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateResponse {
  private final UUID commentId;

  @JsonCreator
  private CommentCreateResponse(@JsonProperty("commentId") UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentCreateResponse create(UUID commentId) {
    return new CommentCreateResponse(commentId);
  }
}
