package com.example.community.security.authentication.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtProperties {
  private final String secretKey;
  private final long accessTokenValidityMs;
  private final long refreshTokenValidityMs;

  @ConstructorBinding
  public JwtProperties(String secretKey, long accessTokenValidityMs, long refreshTokenValidityMs) {
    this.secretKey = secretKey;
    this.accessTokenValidityMs = accessTokenValidityMs;
    this.refreshTokenValidityMs = refreshTokenValidityMs;
  }
}