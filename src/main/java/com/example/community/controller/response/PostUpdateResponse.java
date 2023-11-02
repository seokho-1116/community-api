package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostUpdateResponse {
  private final UUID postId;

  @JsonCreator
  private PostUpdateResponse(@JsonProperty("postId") UUID postId) {
    this.postId = postId;
  }

  public static PostUpdateResponse create(UUID postId) {
    return new PostUpdateResponse(postId);
  }
}
