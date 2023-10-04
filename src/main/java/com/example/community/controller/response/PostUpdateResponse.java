package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class PostUpdateResponse {
  private final String postId;

  private PostUpdateResponse(String postId) {
    this.postId = postId;
  }

  public static PostUpdateResponse create(String postId) {
    return new PostUpdateResponse(postId);
  }
}
