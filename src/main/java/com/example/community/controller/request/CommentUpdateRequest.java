package com.example.community.controller.request;

import com.example.community.service.dto.CommentUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {
  private final String content;

  @JsonCreator
  public CommentUpdateRequest(@JsonProperty("content") String content) {
    this.content = content;
  }

  public CommentUpdateRequestDto toDto(UUID boardPublicId, UUID postPublicId, UUID commentPublicId) {
    return CommentUpdateRequestDto.create(boardPublicId, postPublicId, commentPublicId, content);
  }
}
