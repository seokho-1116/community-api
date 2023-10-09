package com.example.community.controller.response.factory;

import com.example.community.controller.response.CommentDetailResponse;
import com.example.community.controller.response.PageResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.service.dto.CommentDetailDto;
import com.example.community.service.dto.PostSummaryDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class PageResponseFactory {
  public static PageResponse<PostSummaryResponse> createPostsPageResponse(
      Page<PostSummaryDto> page) {
    List<PostSummaryResponse> content = page.getContent()
        .stream()
        .map(PostResponseFactory::createPostSummaryResponse)
        .collect(Collectors.toList());

    return PageResponse.create(content, page);
  }

  public static PageResponse<CommentDetailResponse> createCommentsPageResponse(
      Page<CommentDetailDto> page) {
    List<CommentDetailResponse> content = page.stream()
        .map(CommentDetailResponse::createFrom)
        .collect(Collectors.toList());

    return PageResponse.create(content, page);
  }
}
