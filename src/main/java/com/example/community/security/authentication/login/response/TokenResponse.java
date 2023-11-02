package com.example.community.security.authentication.login.response;

import com.example.community.service.dto.TokenResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TokenResponse {
  private static final String TOKEN_TYPE = "Bearer ";
  private final String accessToken;
  private final UUID refreshTokenPublicId;
  private final String expiresIn;

  @JsonCreator
  private TokenResponse(@JsonProperty("accessToken") String accessToken,
      @JsonProperty("refreshTokenPublicId") UUID refreshTokenPublicId,
      @JsonProperty("expiresIn") String expiresIn) {
    this.accessToken = accessToken;
    this.refreshTokenPublicId = refreshTokenPublicId;
    this.expiresIn = expiresIn;
  }

  public static TokenResponse create(TokenResponseDto dto) {
    return new TokenResponse(TOKEN_TYPE + dto.getAccessToken(),
        dto.getRefreshTokenPublicId(), dto.getExpiresIn());
  }
}
