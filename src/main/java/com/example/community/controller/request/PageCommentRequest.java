package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PageCommentRequest {
  private final String previousDate;
  private final int page;
  private final int size;

  @JsonCreator
  public PageCommentRequest(@JsonProperty("previousDate") String previousDate,
      @JsonProperty("page") int page, @JsonProperty("size") int size) {
    this.previousDate = previousDate;
    this.page = page;
    this.size = size;
  }
}
