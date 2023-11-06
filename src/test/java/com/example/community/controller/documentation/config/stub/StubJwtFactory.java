package com.example.community.controller.documentation.config.stub;

import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.JwtProperties;
import com.example.community.security.authentication.jwt.TokenType;

public class StubJwtFactory extends JwtFactory {
  public StubJwtFactory(String key) {
    super(new JwtProperties(key, 0, 36000));
  }

  @Override
  public String createAccessToken(String memberId, String role) {
    return "access";
  }

  @Override
  public String createRefreshToken(String memberId, String role) {
    return "refresh";
  }

  @Override
  public String getPayload(TokenType type, String token, String claimName) {
    return "71239da8-8d81-41cf-9328-5e754d8e6c80";
  }

  @Override
  public boolean isValid(TokenType type, String token) {
    return true;
  }

  @Override
  public String getExpiresIn() {
    return super.getExpiresIn();
  }
}
