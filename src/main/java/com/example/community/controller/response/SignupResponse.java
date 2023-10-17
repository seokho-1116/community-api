package com.example.community.controller.response;

import java.util.UUID;
import lombok.Getter;

@Getter
public class SignupResponse {
  private final UUID memberId;

  private SignupResponse(UUID memberId) {
    this.memberId = memberId;
  }

  public static SignupResponse create(UUID publicId) {
    return new SignupResponse(publicId);
  }
}
