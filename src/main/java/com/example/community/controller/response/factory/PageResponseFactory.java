package com.example.community.controller.response.factory;

import com.example.community.controller.response.PageResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.service.dto.PostSummaryDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class PageResponseFactory {
  public static PageResponse<PostSummaryResponse> createPostsPageResponse(Page<PostSummaryDto> page) {
    List<PostSummaryResponse> content = page.getContent()
        .stream()
        .map(PostResponseFactory::createPostSummaryResponse)
        .collect(Collectors.toList());

    return PageResponse.create(content, page);
  }
}
