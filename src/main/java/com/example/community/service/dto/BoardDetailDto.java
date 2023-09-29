package com.example.community.service.dto;

import java.time.ZonedDateTime;
import lombok.Getter;

@Getter
public class BoardDetailDto {
  private final String publicId;
  private final String name;
  private final String description;
  private final ZonedDateTime createdDate;

  public BoardDetailDto(String publicId, String name, String description,
      ZonedDateTime createdDate) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
  }
}
