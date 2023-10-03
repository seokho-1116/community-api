package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PagePostRequest {
  private String previousDate;
  private int page;
  private int size;

  @JsonCreator
  public PagePostRequest(@JsonProperty("previousDate") String previousDate,
      @JsonProperty("page") int page, @JsonProperty("size") int size) {
    this.previousDate = previousDate;
    this.page = page;
    this.size = size;
  }
}
