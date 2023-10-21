package com.example.community.controller.request;

import com.example.community.service.dto.PostUpdateDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostUpdateRequest {
  private final String title;
  private final String content;

  @JsonCreator
  public PostUpdateRequest(@JsonProperty("title") String title,
      @JsonProperty("content") String content) {
    this.title = title;
    this.content = content;
  }

  public PostUpdateDto toDto(UUID boardId, UUID postId) {
    return new PostUpdateDto(title, content, boardId, postId);
  }
}
