package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {
  private final String password;

  @JsonCreator
  public PasswordUpdateRequest(@JsonProperty("password") String password) {
    this.password = password;
  }
}
