package com.example.community.controller.response;

import lombok.Getter;

@Getter
public abstract class PageResponse {
  private final long totalElements;
  private final int totalPages;

  private final int number;
  private final int numberOfElements;

  private final boolean hasNext;
  private final boolean hasPrevious;

  protected PageResponse(long totalElements, int totalPages, int number, int numberOfElements,
      boolean hasNext, boolean hasPrevious) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.number = number;
    this.numberOfElements = numberOfElements;
    this.hasNext = hasNext;
    this.hasPrevious = hasPrevious;
  }
}
