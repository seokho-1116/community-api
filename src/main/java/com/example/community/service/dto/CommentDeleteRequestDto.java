package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentDeleteRequestDto {
  private final UUID boardPublicId;
  private final UUID postPublicId;
  private final UUID commentPublicId;
  private final UUID memberPublicId;

  private CommentDeleteRequestDto(UUID boardPublicId, UUID postPublicId, UUID commentPublicId,
      UUID memberPublicId) {
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
    this.commentPublicId = commentPublicId;
    this.memberPublicId = memberPublicId;
  }

  public static CommentDeleteRequestDto create(UUID boardPublicId, UUID postPublicId,
      UUID commentPublicId, UUID memberPublicId) {
    return new CommentDeleteRequestDto(boardPublicId, postPublicId, commentPublicId,
        memberPublicId);
  }
}
