package com.example.community.controller.response;

import com.example.community.service.dto.TokenRefreshResponseDto;
import lombok.Getter;

@Getter
public class TokenRefreshResponse {
  private final String accessToken;
  private final String expiresIn;

  private TokenRefreshResponse(String accessToken, String expiresIn) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }

  public static TokenRefreshResponse create(TokenRefreshResponseDto dto) {
    return new TokenRefreshResponse(dto.getAccessToken(), dto.getExpiresIn());
  }
}
