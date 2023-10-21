package com.example.community.service.dto;

import lombok.Getter;

@Getter
public class BoardSummaryResponseDto {
  private final String publicId;
  private final String name;

  public BoardSummaryResponseDto(String publicId, String name) {
    this.publicId = publicId;
    this.name = name;
  }
}
