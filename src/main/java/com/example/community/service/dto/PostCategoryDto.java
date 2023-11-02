package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCategoryDto {
  private final UUID publicId;
  private final String name;
  private final String description;

  public PostCategoryDto(UUID publicId, String name, String description) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
  }
}
