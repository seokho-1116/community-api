package com.example.community.service.dto;

import lombok.Getter;

@Getter
public class TokenRefreshResponseDto {
  private final String accessToken;
  private final String expiresIn;

  public TokenRefreshResponseDto(String accessToken, String expiresIn) {
    this.accessToken = accessToken;
    this.expiresIn = expiresIn;
  }

  public static TokenRefreshResponseDto create(String accessToken, String expiresIn) {
    return new TokenRefreshResponseDto(accessToken, expiresIn);
  }
}
