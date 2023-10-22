package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostDeleteResponse {
  private final UUID postId;

  private PostDeleteResponse(UUID postId) {
    this.postId = postId;
  }

  public static PostDeleteResponse create(UUID postId) {
    return new PostDeleteResponse(postId);
  }
}
