package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCreateResponse {
  private final UUID postId;

  private PostCreateResponse(UUID postId) {
    this.postId = postId;
  }

  public static PostCreateResponse create(UUID postId) {
    return new PostCreateResponse(postId);
  }
}
