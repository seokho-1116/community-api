package com.example.community.service.dto;

import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class CommentDetailResponseDto {
  private final String nickname;
  private final String content;
  private final OffsetDateTime createdDate;
  private final int upVotesCount;
  private final int downVotesCount;

  public CommentDetailResponseDto(String nickname, String content, OffsetDateTime createdDate,
      int upVotesCount, int downVotesCount) {
    this.nickname = nickname;
    this.content = content;
    this.createdDate = createdDate;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
  }
}
