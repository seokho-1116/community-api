package com.example.community.service.dto;

import io.jsonwebtoken.lang.Assert;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentDetailResponseDto {
  private final UUID memberPublicId;
  private final String nickname;
  private final String content;
  private final OffsetDateTime createdDate;
  private final int upVotesCount;
  private final int downVotesCount;
  private boolean isOwner = false;

  public CommentDetailResponseDto(UUID memberPublicId, String nickname, String content, OffsetDateTime createdDate,
      int upVotesCount, int downVotesCount) {
    this.memberPublicId = memberPublicId;
    this.nickname = nickname;
    this.content = content;
    this.createdDate = createdDate;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
  }

  public void setIsOwner(boolean isOwner) {
    this.isOwner = isOwner;
  }
}
