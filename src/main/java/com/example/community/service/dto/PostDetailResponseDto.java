package com.example.community.service.dto;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostDetailResponseDto {
  private final String publicId;
  private final String title;
  private final String content;
  private final String nickname;
  private final UUID memberPublicId;
  private final OffsetDateTime createdDate;
  private final int viewsCount;
  private final int upVotesCount;
  private final int downVotesCount;
  private final String boardCategory;
  private final String postCategory;
  private final String postURL;
  private boolean isOwner = false;

  public PostDetailResponseDto(String publicId, String title, String content, String nickname,
      UUID memberPublicId, OffsetDateTime createdDate, int viewsCount, int upVotesCount,
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

  public void setIsOwner(boolean isOwner) {
    this.isOwner = isOwner;
  }
}
