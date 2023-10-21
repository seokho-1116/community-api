package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class PageCommentRequest {
  private final OffsetDateTime previousDate;
  private final int size;

  @JsonCreator
  public PageCommentRequest(@JsonProperty("previousDate") OffsetDateTime previousDate,
      @JsonProperty("size") int size) {
    this.previousDate = previousDate;
    this.size = size;
  }
}
