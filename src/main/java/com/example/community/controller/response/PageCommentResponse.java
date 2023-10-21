package com.example.community.controller.response;

import com.example.community.service.dto.CommentDetailResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageCommentResponse extends PageResponse {
  private final List<CommentDetailResponse> content;

  public PageCommentResponse(List<CommentDetailResponse> content, long totalElements, int totalPages,
      int number, int numberOfElements, boolean hasNext, boolean hasPrevious) {
    super(totalElements, totalPages, number, numberOfElements, hasNext, hasPrevious);

    this.content = content;
  }

  public static PageCommentResponse create(Page<CommentDetailResponseDto> page) {
    List<CommentDetailResponse> dtoList = page.getContent().stream()
        .map(CommentDetailResponse::create)
        .collect(Collectors.toList());

    return new PageCommentResponse(dtoList, page.getTotalElements(), page.getTotalPages(),
        page.getNumber(), page.getNumberOfElements(), page.hasNext(), page.hasPrevious());
  }
}
