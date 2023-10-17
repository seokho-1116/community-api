package com.example.community.security.authentication.login.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LoginRequest {
  private final String signupId;
  private final String signupPassword;

  @JsonCreator
  public LoginRequest(@JsonProperty("id") String signupId,
      @JsonProperty("password") String signupPassword) {
    this.signupId = (signupId != null) ? signupId.trim() : "";
    this.signupPassword = (signupPassword != null) ? signupPassword.trim() : "";
  }
}
