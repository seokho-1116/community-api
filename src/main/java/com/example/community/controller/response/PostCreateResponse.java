package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCreateResponse {
  private final UUID postId;

  @JsonCreator
  private PostCreateResponse(@JsonProperty("postId") UUID postId) {
    this.postId = postId;
  }

  public static PostCreateResponse create(UUID postId) {
    return new PostCreateResponse(postId);
  }
}
