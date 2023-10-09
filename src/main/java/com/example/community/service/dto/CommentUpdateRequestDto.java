package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
  private final UUID postId;
  private final UUID commentId;
  private final String content;

  public CommentUpdateRequestDto(UUID postId, UUID commentId, String content) {
    this.postId = postId;
    this.commentId = commentId;
    this.content = content;
  }

  public static CommentUpdateRequestDto create(UUID postId, UUID commentId, String content) {
    return new CommentUpdateRequestDto(postId, commentId, content);
  }
}
