package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostUpdateResponse {
  private final UUID postId;

  private PostUpdateResponse(UUID postId) {
    this.postId = postId;
  }

  public static PostUpdateResponse create(UUID postId) {
    return new PostUpdateResponse(postId);
  }
}
