package com.example.community.controller.request;

import com.example.community.service.dto.PostCreateDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCreateRequest {
  private final String title;
  private final String content;
  private final UUID postCategoryId;

  @JsonCreator
  public PostCreateRequest(@JsonProperty("title") String title,
      @JsonProperty("content") String content,
      @JsonProperty("postCategoryId") UUID postCategoryId) {
    this.title = title;
    this.content = content;
    this.postCategoryId = postCategoryId;
  }

  public PostCreateDto toDto(UUID boardId) {
    return new PostCreateDto(title, content, boardId, postCategoryId);
  }
}
