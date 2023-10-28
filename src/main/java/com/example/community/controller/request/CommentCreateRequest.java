package com.example.community.controller.request;

import com.example.community.service.dto.CommentCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
  @NotEmpty
  private final String content;

  @JsonCreator
  public CommentCreateRequest(@JsonProperty("content") String content) {
    this.content = content;
  }

  public CommentCreateRequestDto toDto(UUID boardPublicId, UUID postPublicId, UUID memberPublicId) {
    return CommentCreateRequestDto.create(boardPublicId, postPublicId, memberPublicId, content);
  }
}
