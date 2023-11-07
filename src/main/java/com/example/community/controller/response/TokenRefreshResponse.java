package com.example.community.controller.response;

import com.example.community.service.dto.TokenRefreshResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenRefreshResponse {
  private final String accessToken;
  private final String expiresIn;

  @JsonCreator
  private TokenRefreshResponse(@JsonProperty("accessToken") String accessToken,
      @JsonProperty("expiresIn") String expiresIn) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }

  public static TokenRefreshResponse create(TokenRefreshResponseDto dto) {
    return new TokenRefreshResponse(dto.getAccessToken(), dto.getExpiresIn());
  }
}
