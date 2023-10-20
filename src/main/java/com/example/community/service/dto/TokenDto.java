package com.example.community.service.dto;

import java.beans.ConstructorProperties;
import lombok.Getter;

@Getter
public class TokenDto {
  private final String refreshToken;
  private final String memberPublicId;
  private final String role;

  @ConstructorProperties({"refreshToken", "memberPublicId", "role"})
  public TokenDto(String refreshToken, String memberPublicId, String role) {
    this.refreshToken = refreshToken;
    this.memberPublicId = memberPublicId;
    this.role = role;
  }
}
