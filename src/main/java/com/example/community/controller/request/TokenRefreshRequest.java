package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TokenRefreshRequest {
  @NotNull
  private final UUID refreshTokenPublicId;

  @JsonCreator
  public TokenRefreshRequest(@JsonProperty("refreshTokenPublicId") UUID refreshTokenPublicId) {
    this.refreshTokenPublicId = refreshTokenPublicId;
  }
}
