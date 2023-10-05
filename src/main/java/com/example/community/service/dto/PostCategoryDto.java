package com.example.community.service.dto;

import lombok.Getter;

@Getter
public class PostCategoryDto {
  private final String name;
  private final String description;

  public PostCategoryDto(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
