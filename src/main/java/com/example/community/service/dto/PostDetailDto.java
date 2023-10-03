package com.example.community.service.dto;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class PostDetailDto {
  private final String publicId;
  private final String title;
  private final String content;
  private final String nickname;
  private final String memberPublicId;
  private final OffsetDateTime createdDate;
  private final int viewsCount;
  private final int upVotesCount;
  private final int downVotesCount;
  private final String boardCategory;
  private final String postCategory;
  private final String postURL;

  public PostDetailDto(String publicId, String title, String content, String nickname,
      String memberPublicId, OffsetDateTime createdDate, int viewsCount, int upVotesCount,
      int downVotesCount, String boardCategory, String postCategory, String postURL) {
    this.publicId = publicId;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.memberPublicId = memberPublicId;
    this.createdDate = createdDate;
    this.viewsCount = viewsCount;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
    this.boardCategory = boardCategory;
    this.postCategory = postCategory;
    this.postURL = postURL;
  }
}
