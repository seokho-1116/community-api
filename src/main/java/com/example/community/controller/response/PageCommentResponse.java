package com.example.community.controller.response;

import com.example.community.service.dto.CommentDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageCommentResponse extends PageResponse {
  private final List<CommentDetailResponse> content;

  @JsonCreator
  private PageCommentResponse(@JsonProperty("content") List<CommentDetailResponse> content,
      @JsonProperty("totalElements") long totalElements, @JsonProperty("totalPages") int totalPages,
      @JsonProperty("number") int number, @JsonProperty("numberOfElements") int numberOfElements,
      @JsonProperty("hasNext") boolean hasNext, @JsonProperty("hasPrevious") boolean hasPrevious) {
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
