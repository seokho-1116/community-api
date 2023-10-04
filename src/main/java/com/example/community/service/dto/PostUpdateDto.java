package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class PostUpdateDto {
  private final String title;
  private final String content;
  private final UUID boardId;
  private final UUID postId;

  public PostUpdateDto(String title, String content, UUID boardId, UUID postId) {
    this.title = title;
    this.content = content;
    this.boardId = boardId;
    this.postId = postId;
  }
}
