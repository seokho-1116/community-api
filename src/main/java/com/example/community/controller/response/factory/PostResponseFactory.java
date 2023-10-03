package com.example.community.controller.response.factory;

import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.service.dto.PostDetailDto;
import com.example.community.service.dto.PostSummaryDto;

public class PostResponseFactory {
  public static PostSummaryResponse createPostSummaryResponse(PostSummaryDto dto) {
    return PostSummaryResponse.create(dto.getPublicId(), dto.getTitle(), dto.getContent(),
        dto.getNickname(), dto.getViewsCount(), dto.getBoardCategory(), dto.getPostCategory(),
        dto.getBoardId(), dto.getCreatedDate());
  }

  public static PostDetailResponse createPostDetailResponse(PostDetailDto dto) {
    return new PostDetailResponse(dto.getPublicId(), dto.getTitle(), dto.getContent(),
        dto.getNickname(), dto.getCreatedDate(), dto.getViewsCount(), dto.getUpVotesCount(),
        dto.getDownVotesCount(), dto.getBoardCategory(), dto.getPostCategory(), dto.getPostURL());
  }
}
