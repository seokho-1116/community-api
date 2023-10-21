package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
  private final UUID postPublicId;
  private final UUID commentPublicId;
  private final String content;

  public CommentUpdateRequestDto(UUID postPublicId, UUID commentPublicId, String content) {
    this.postPublicId = postPublicId;
    this.commentPublicId = commentPublicId;
    this.content = content;
  }

  public static CommentUpdateRequestDto create(UUID postId, UUID commentId, String content) {
    return new CommentUpdateRequestDto(postId, commentId, content);
  }
}
