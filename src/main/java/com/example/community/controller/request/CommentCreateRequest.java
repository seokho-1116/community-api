package com.example.community.controller.request;

import com.example.community.service.dto.CommentCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
  private final UUID memberPublicId;
  private final String content;

  @JsonCreator
  public CommentCreateRequest(@JsonProperty("memberId") UUID memberPublicId,
      @JsonProperty("content") String content) {
    this.memberPublicId = memberPublicId;
    this.content = content;
  }

  public CommentCreateRequestDto toDto(UUID boardPublicId, UUID postPublicId) {
    return CommentCreateRequestDto.create(boardPublicId, postPublicId, memberPublicId, content);
  }
}
