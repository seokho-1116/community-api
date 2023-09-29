package com.example.community.service.dto;

import lombok.Getter;

@Getter
public class BoardSummaryDto {
  private final String publicId;
  private final String name;

  public BoardSummaryDto(String publicId, String name) {
    this.publicId = publicId;
    this.name = name;
  }
}
