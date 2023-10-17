package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class EmailUpdateResponse {
  private final String email;

  private EmailUpdateResponse(String email) {
    this.email = email;
  }

  public static EmailUpdateResponse create(String email) {
    return new EmailUpdateResponse(email);
  }
}
