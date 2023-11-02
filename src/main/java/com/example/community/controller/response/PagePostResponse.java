package com.example.community.controller.response;

import com.example.community.service.dto.PostSummaryResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PagePostResponse extends PageResponse {
  private final List<PostSummaryResponse> content;

  @JsonCreator
  private PagePostResponse(@JsonProperty("content") List<PostSummaryResponse> content,
      @JsonProperty("totalElements") long totalElements, @JsonProperty("totalPages") int totalPages,
      @JsonProperty("number") int number, @JsonProperty("numberOfElements") int numberOfElements,
      @JsonProperty("hasNext") boolean hasNext, @JsonProperty("hasPrevious") boolean hasPrevious) {
    super(totalElements, totalPages, number, numberOfElements, hasNext, hasPrevious);

    this.content = content;
  }

  public static PagePostResponse create(Page<PostSummaryResponseDto> page) {
    List<PostSummaryResponse> dtoList = page.getContent().stream()
        .map(PostSummaryResponse::create)
        .collect(Collectors.toList());

    return new PagePostResponse(dtoList, page.getTotalElements(), page.getTotalPages(),
        page.getNumber(), page.getNumberOfElements(), page.hasNext(), page.hasPrevious());
  }
}
