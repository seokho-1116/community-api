package com.example.community.controller.response;

import com.example.community.service.dto.CommentDetailResponseDto;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class CommentDetailResponse {
  private final String nickname;
  private final String content;
  private final OffsetDateTime createdDate;
  private final int upVotesCount;
  private final int downVotesCount;

  private CommentDetailResponse(String nickname, String content, OffsetDateTime createdDate,
      int upVotesCount, int downVotesCount) {
    this.nickname = nickname;
    this.content = content;
    this.createdDate = createdDate;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
  }

  public static CommentDetailResponse create(CommentDetailResponseDto dto) {
    return new CommentDetailResponse(dto.getNickname(), dto.getContent(), dto.getCreatedDate(),
        dto.getUpVotesCount(), dto.getDownVotesCount());
  }
}
