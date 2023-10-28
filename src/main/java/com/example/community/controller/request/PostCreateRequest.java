package com.example.community.controller.request;

import com.example.community.service.dto.PostCreateDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PostCreateRequest {
  @NotEmpty
  private final String title;

  @NotEmpty
  private final String content;

  @NotNull
  private final UUID postCategoryPublicId;

  @JsonCreator
  public PostCreateRequest(@JsonProperty("title") String title,
      @JsonProperty("content") String content,
      @JsonProperty("postCategoryId") UUID postCategoryPublicId) {
    this.title = title;
    this.content = content;
    this.postCategoryPublicId = postCategoryPublicId;
  }

  public PostCreateDto toDto(UUID boardPublicId, UUID memberPublicId) {
    return new PostCreateDto(title, content, boardPublicId, postCategoryPublicId, memberPublicId);
  }
}
