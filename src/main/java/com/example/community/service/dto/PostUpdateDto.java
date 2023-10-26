package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostUpdateDto {
  private final String title;
  private final String content;
  private final UUID boardPublicId;
  private final UUID postPublicId;
  private final UUID memberPublicId;

  public PostUpdateDto(String title, String content, UUID boardPublicId, UUID postPublicId,
      UUID memberPublicId) {
    this.title = title;
    this.content = content;
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
    this.memberPublicId = memberPublicId;
  }
}
