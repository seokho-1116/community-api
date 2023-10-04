package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class PostCreateResponse {
  private final String postId;

  private PostCreateResponse(String postId) {
    this.postId = postId;
  }

  public static PostCreateResponse create(String postId) {
    return new PostCreateResponse(postId);
  }
}
