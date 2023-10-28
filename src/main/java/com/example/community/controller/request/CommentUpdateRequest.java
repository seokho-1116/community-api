package com.example.community.controller.request;

import com.example.community.service.dto.CommentUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {
  @NotEmpty
  private final String content;

  @JsonCreator
  public CommentUpdateRequest(@JsonProperty("content") String content) {
    this.content = content;
  }

  public CommentUpdateRequestDto toDto(UUID boardPublicId, UUID postPublicId, UUID commentPublicId,
      UUID memberPublicId) {
    return CommentUpdateRequestDto.create(boardPublicId, postPublicId, commentPublicId,
        memberPublicId, content);
  }
}
