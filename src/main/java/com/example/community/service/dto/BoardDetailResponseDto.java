package com.example.community.service.dto;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class BoardDetailResponseDto {
  private final String publicId;
  private final String name;
  private final String description;
  private final OffsetDateTime createdDate;

  public BoardDetailResponseDto(String publicId, String name, String description,
      OffsetDateTime createdDate) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
  }
}
