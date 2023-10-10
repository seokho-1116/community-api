package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class PostDeleteResponse {
  private final String postId;

  private PostDeleteResponse(String postId) {
    this.postId = postId;
  }

  public static PostDeleteResponse create(String postId) {
    return new PostDeleteResponse(postId);
  }
}
