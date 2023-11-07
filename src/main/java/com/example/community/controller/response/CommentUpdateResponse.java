package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateResponse {
  private final UUID commentId;

  @JsonCreator
  private CommentUpdateResponse(@JsonProperty("commentId") UUID commentId) {
    this.commentId = commentId;
  }

  public static CommentUpdateResponse create(UUID publicId) {
    return new CommentUpdateResponse(publicId);
  }
}
