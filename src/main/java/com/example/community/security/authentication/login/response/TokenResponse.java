package com.example.community.security.authentication.login.response;

import lombok.Getter;

@Getter
public class TokenResponse {
  private final String accessToken;
  private final String refreshToken;
  private final String expiresIn;

  private TokenResponse(String accessToken, String refreshToken, String expiresIn) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.expiresIn = expiresIn;
  }

  public static TokenResponse create(String accessToken, String refreshToken,
      String expiresIn) {
    return new TokenResponse(accessToken, refreshToken, expiresIn);
  }
}
