package com.example.community.controller.response;

import com.example.community.service.dto.PostDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  private final boolean isOwner;

  @JsonCreator
  private PostDetailResponse(@JsonProperty("publicId") String publicId,
      @JsonProperty("title") String title, @JsonProperty("content") String content,
      @JsonProperty("nickname") String nickname,
      @JsonProperty("createdDate") OffsetDateTime createdDate,
      @JsonProperty("viewsCount") int viewsCount, @JsonProperty("upVotesCount") int upVotesCount,
      @JsonProperty("downVotesCount") int downVotesCount,
      @JsonProperty("boardCategory") String boardCategory,
      @JsonProperty("postCategory") String postCategory, @JsonProperty("postURL") String postURL,
      @JsonProperty("isOwner") boolean isOwner) {
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
    this.isOwner = isOwner;
  }

  public static PostDetailResponse create(PostDetailResponseDto dto) {
    return new PostDetailResponse(dto.getPublicId(), dto.getTitle(), dto.getContent(),
        dto.getNickname(), dto.getCreatedDate(), dto.getViewsCount(), dto.getUpVotesCount(),
        dto.getDownVotesCount(), dto.getBoardCategory(), dto.getPostCategory(), dto.getPostURL(),
        dto.isOwner());
  }

  @JsonProperty("isOwner")
  public boolean isOwner() {
    return isOwner;
  }
}
