package com.example.community.controller.response;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class BoardDetailResponse {
  private final String publicId;
  private final String name;
  private final String description;
  private final OffsetDateTime createdDate;

  public BoardDetailResponse(String publicId, String name, String description,
      OffsetDateTime createdDate) {
    this.publicId = publicId;
    this.name = name;
    this.description = description;
    this.createdDate = createdDate;
  }
}
