package com.example.community.security.authentication.login.response;

import com.example.community.service.dto.TokenResponseDto;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TokenResponse {
  private final String accessToken;
  private final UUID refreshTokenPublicId;
  private final String expiresIn;

  private TokenResponse(String accessToken, UUID refreshTokenPublicId, String expiresIn) {
    this.accessToken = accessToken;
    this.refreshTokenPublicId = refreshTokenPublicId;
    this.expiresIn = expiresIn;
  }

  public static TokenResponse create(TokenResponseDto dto) {
    return new TokenResponse(dto.getAccessToken(), dto.getRefreshTokenPublicId(), dto.getExpiresIn());
  }
}
