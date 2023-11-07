package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EmailUpdateResponse {
  private final String email;

  @JsonCreator
  private EmailUpdateResponse(@JsonProperty("email") String email) {
    this.email = email;
  }

  public static EmailUpdateResponse create(String email) {
    return new EmailUpdateResponse(email);
  }
}
