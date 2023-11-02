package com.example.community.controller.response;

import com.example.community.service.dto.PostCategoryDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostCategoryResponse {
  private final UUID publicId;
  private final String name;
  private final String description;

  @JsonCreator
  private PostCategoryResponse(@JsonProperty("publicId") UUID publicId,
      @JsonProperty("name") String name, @JsonProperty("description") String description) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
  }

  public static List<PostCategoryResponse> create(List<PostCategoryDto> dtoList) {
    return dtoList.stream()
        .map(dto -> new PostCategoryResponse(dto.getPublicId(), dto.getName(), dto.getDescription()))
        .collect(Collectors.toList());
  }
}
