package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class BoardSummaryResponse {
  private final String publicId;
  private final String name;

  public BoardSummaryResponse(String publicId, String name) {
    this.publicId = publicId;
    this.name = name;
  }
}
