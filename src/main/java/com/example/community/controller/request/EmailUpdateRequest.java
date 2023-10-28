package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class EmailUpdateRequest {
  @Email
  @NotEmpty
  private final String email;

  @JsonCreator
  public EmailUpdateRequest(@JsonProperty("email") String email) {
    this.email = email;
  }
}
