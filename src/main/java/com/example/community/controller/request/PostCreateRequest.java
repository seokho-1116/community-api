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
  private final String postCategoryId;

  @JsonCreator
  public PostCreateRequest(@JsonProperty("title") String title,
      @JsonProperty("content") String content,
      @JsonProperty("postCategoryId") String postCategoryId) {
    this.title = title;
    this.content = content;
    this.postCategoryId = postCategoryId;
  }

  public PostCreateDto toDto(String boardId) {
    return new PostCreateDto(title, content, UUID.fromString(boardId),
        UUID.fromString(postCategoryId));
  }
}
