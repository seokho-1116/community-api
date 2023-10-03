package com.example.community.controller.response.factory;

import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.service.dto.PostSummaryDto;

public class PostResponseFactory {
  public static PostSummaryResponse toResponse(PostSummaryDto dto) {
    return PostSummaryResponse.create(dto.getPublicId(), dto.getTitle(), dto.getContent(),
        dto.getNickname(), dto.getViewsCount(), dto.getBoardCategory(), dto.getPostCategory(),
        dto.getBoardId(), dto.getCreatedDate());
  }
}
