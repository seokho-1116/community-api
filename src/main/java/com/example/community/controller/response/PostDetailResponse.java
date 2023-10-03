package com.example.community.controller.response;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class PostDetailResponse {
  private final String publicId;
  private final String title;
  private final String content;
  private final String nickname;
  private final OffsetDateTime createdDate;
  private final int viewsCount;
  private final int upVotesCount;
  private final int downVotesCount;
  private final String boardCategory;
  private final String postCategory;
  private final String postURL;

  public PostDetailResponse(String publicId, String title, String content, String nickname,
      OffsetDateTime createdDate, int viewsCount, int upVotesCount, int downVotesCount,
      String boardCategory, String postCategory, String postURL) {
    this.publicId = publicId;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.createdDate = createdDate;
    this.viewsCount = viewsCount;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
    this.boardCategory = boardCategory;
    this.postCategory = postCategory;
    this.postURL = postURL;
  }
}
