package com.example.community.controller.response;

import com.example.community.service.dto.CommentDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class CommentDetailResponse {
  private final String nickname;
  private final String content;
  private final OffsetDateTime createdDate;
  private final int upVotesCount;
  private final int downVotesCount;
  private final boolean isOwner;

  @JsonCreator
  private CommentDetailResponse(@JsonProperty("nickname") String nickname,
      @JsonProperty("content") String content,
      @JsonProperty("createdDate") OffsetDateTime createdDate,
      @JsonProperty("upVotesCount") int upVotesCount,
      @JsonProperty("downVotesCount") int downVotesCount,
      @JsonProperty("isOwner") boolean isOwner)
  {
    this.nickname = nickname;
    this.content = content;
    this.createdDate = createdDate;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
    this.isOwner = isOwner;
  }

  public static CommentDetailResponse create(CommentDetailResponseDto dto) {
    return new CommentDetailResponse(dto.getNickname(), dto.getContent(), dto.getCreatedDate(),
        dto.getUpVotesCount(), dto.getDownVotesCount(), dto.isOwner());
  }

  @JsonProperty("isOwner")
  public boolean isOwner() {
    return isOwner;
  }
}
