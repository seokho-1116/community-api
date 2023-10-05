package com.example.community.controller.response;

import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostCategoryResponse {
  private final String name;
  private final String description;

  private PostCategoryResponse(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public static List<PostCategoryResponse> create(List<PostCategoryDto> dtos) {
    return dtos.stream()
        .map(dto -> new PostCategoryResponse(dto.getName(), dto.getDescription()))
        .collect(Collectors.toList());
  }
}
