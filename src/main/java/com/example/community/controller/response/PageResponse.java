package com.example.community.controller.response;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageResponse<T> {
  private final List<T> content;

  private final long totalElements;
  private final int totalPages;

  private final int number;
  private final int numberOfElements;

  private final boolean hasNext;
  private final boolean hasPrevious;

  private PageResponse(List<T> content, long totalElements, int totalPages, int number,
      int numberOfElements, boolean hasNext, boolean hasPrevious) {
    this.content = content;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.number = number;
    this.numberOfElements = numberOfElements;
    this.hasNext = hasNext;
    this.hasPrevious = hasPrevious;
  }

  public static <T> PageResponse<T> create(List<T> content, Page<?> page) {
    return new PageResponse<>(content, page.getTotalElements(), page.getTotalPages(),
        page.getNumber(), page.getNumberOfElements(), page.hasNext(), page.hasPrevious());
  }
}
