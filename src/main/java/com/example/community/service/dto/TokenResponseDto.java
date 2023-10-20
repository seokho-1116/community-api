package com.example.community.service.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class TokenResponseDto {
  private final String accessToken;
  private final UUID refreshTokenPublicId;
  private final String expiresIn;

  public TokenResponseDto(String accessToken, UUID refreshTokenPublicId, String expiresIn) {
    this.accessToken = accessToken;
    this.refreshTokenPublicId = refreshTokenPublicId;
    this.expiresIn = expiresIn;
  }

  public static TokenResponseDto create(String accessToken, UUID refreshToken, String expiresIn) {
    return new TokenResponseDto(accessToken, refreshToken, expiresIn);
  }
}
