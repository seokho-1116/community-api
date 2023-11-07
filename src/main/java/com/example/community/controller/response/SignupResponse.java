package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class SignupResponse {
  private final UUID memberId;

  @JsonCreator
  private SignupResponse(@JsonProperty("memberId") UUID memberId) {
    this.memberId = memberId;
  }

  public static SignupResponse create(UUID publicId) {
    return new SignupResponse(publicId);
  }
}
