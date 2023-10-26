package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
  private final UUID boardPublicId;
  private final UUID postPublicId;
  private final UUID commentPublicId;
  private final UUID memberPublicId;
  private final String content;

  private CommentUpdateRequestDto(UUID boardPublicId, UUID postPublicId, UUID commentPublicId,
      UUID memberPublicId, String content) {
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
    this.commentPublicId = commentPublicId;
    this.memberPublicId = memberPublicId;
    this.content = content;
  }

  public static CommentUpdateRequestDto create(UUID boardPublicId, UUID postId, UUID commentId,
      UUID memberPublicId, String content) {
    return new CommentUpdateRequestDto(boardPublicId, postId, commentId, memberPublicId, content);
  }
}
