package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PasswordUpdateRequest {
  @NotEmpty
  private final String password;

  @JsonCreator
  public PasswordUpdateRequest(@JsonProperty("password") String password) {
    this.password = password;
  }
}
