package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostDeleteResponse {
  private final UUID postId;

  @JsonCreator
  private PostDeleteResponse(@JsonProperty("postId") UUID postId) {
    this.postId = postId;
  }

  public static PostDeleteResponse create(UUID postId) {
    return new PostDeleteResponse(postId);
  }
}
