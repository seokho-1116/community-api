package com.example.community.service.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PageCommentRequestDto {
  private final OffsetDateTime previousDate;
  private final int size;
  private final UUID boardPublicId;
  private final UUID postPublicId;

  private PageCommentRequestDto(OffsetDateTime previousDate, int size, UUID boardPublicId,
      UUID postPublicId) {
    this.previousDate = previousDate;
    this.size = size;
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
  }

  public static PageCommentRequestDto create(OffsetDateTime previousDate, int size,
      UUID boardPublicId, UUID postPublicId) {
    return new PageCommentRequestDto(previousDate, size, boardPublicId, postPublicId);
  }
}
