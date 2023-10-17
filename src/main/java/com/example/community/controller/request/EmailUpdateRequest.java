package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EmailUpdateRequest {
  private final String email;

  @JsonCreator
  public EmailUpdateRequest(@JsonProperty("email") String email) {
    this.email = email;
  }
}
