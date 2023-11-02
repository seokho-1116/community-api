package com.example.community.controller.response;

import com.example.community.service.dto.PostSummaryResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class PostSummaryResponse {
  private final String publicId;
  private final String title;
  private final String content;
  private final String nickname;
  private final long viewsCount;
  private final String boardId;
  private final String boardCategory;
  private final String postCategory;
  private final OffsetDateTime createdDate;

  @JsonCreator
  private PostSummaryResponse(@JsonProperty("publicId") String publicId,
      @JsonProperty("title") String title, @JsonProperty("content") String content,
      @JsonProperty("nickname") String nickname, @JsonProperty("viewsCount") long viewsCount,
      @JsonProperty("boardId") String boardId, @JsonProperty("boardCategory") String boardCategory,
      @JsonProperty("postCategory") String postCategory,
      @JsonProperty("createdDate") OffsetDateTime createdDate) {
    this.publicId = publicId;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.viewsCount = viewsCount;
    this.boardId = boardId;
    this.boardCategory = boardCategory;
    this.postCategory = postCategory;
    this.createdDate = createdDate;
  }

  public static PostSummaryResponse create(PostSummaryResponseDto dto) {
    return new PostSummaryResponse(dto.getPublicId(), dto.getTitle(), dto.getContent(),
        dto.getNickname(), dto.getViewsCount(), dto.getBoardId(), dto.getBoardCategory(),
        dto.getPostCategory(), dto.getCreatedDate());
  }
}
