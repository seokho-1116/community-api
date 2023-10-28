package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PagePostRequest {
  @NotNull
  private final OffsetDateTime previousDate;

  @NotNull
  private final int size;

  @JsonCreator
  public PagePostRequest(@JsonProperty("previousDate") OffsetDateTime previousDate,
      @JsonProperty("size") int size) {
    this.previousDate = previousDate;
    this.size = size;
  }
}
