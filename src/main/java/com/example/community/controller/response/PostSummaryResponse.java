package com.example.community.controller.response;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class PostSummaryResponse {
  private final String publicId;
  private final String title;
  private final String content;
  private final String nickname;
  private final long viewsCount;
  private final String boardCategory;
  private final String postCategory;
  private final OffsetDateTime createdDate;

  private PostSummaryResponse(String publicId, String title, String content, String nickname,
      long viewsCount, String boardCategory, String postCategory, OffsetDateTime createdDate) {
    this.publicId = publicId;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.viewsCount = viewsCount;
    this.boardCategory = boardCategory;
    this.postCategory = postCategory;
    this.createdDate = createdDate;
  }

  public static PostSummaryResponse create(String publicId, String title, String content,
      String userId, long viewCount, String boardCategory, String postCategory,
      OffsetDateTime createdDate) {
    return new PostSummaryResponse(publicId, title, content, userId, viewCount, boardCategory,
        postCategory, createdDate);
  }
}
