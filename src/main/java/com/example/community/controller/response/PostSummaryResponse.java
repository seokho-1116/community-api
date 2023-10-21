package com.example.community.controller.response;

import com.example.community.service.dto.PostSummaryResponseDto;
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

  private PostSummaryResponse(String publicId, String title, String content, String nickname,
      long viewsCount, String boardId, String boardCategory, String postCategory, OffsetDateTime createdDate) {
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
