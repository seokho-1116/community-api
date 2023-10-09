package com.example.community.controller.request;

import com.example.community.service.dto.CommentCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
  private final UUID memberId;
  private final String content;

  @JsonCreator
  public CommentCreateRequest(@JsonProperty("memberId") UUID memberId,
      @JsonProperty("content") String content) {
    this.memberId = memberId;
    this.content = content;
  }

  public CommentCreateRequestDto toDto(UUID postId) {
    return CommentCreateRequestDto.create(postId, memberId, content);
  }
}
