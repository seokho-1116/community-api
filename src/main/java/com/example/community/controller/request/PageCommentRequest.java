package com.example.community.controller.request;

import com.example.community.service.dto.PageCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PageCommentRequest {
  private final OffsetDateTime previousDate;
  private final int size;

  @JsonCreator
  public PageCommentRequest(@JsonProperty("previousDate") OffsetDateTime previousDate,
      @JsonProperty("size") int size) {
    this.previousDate = previousDate;
    this.size = size;
  }

  public PageCommentRequestDto toDto(UUID boardPublicId, UUID postPublicId, UUID memberPublicId) {
    return PageCommentRequestDto.create(previousDate, size, boardPublicId, postPublicId,
        memberPublicId);
  }
}
