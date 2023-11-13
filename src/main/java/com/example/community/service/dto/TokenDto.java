package com.example.community.service.dto;

import java.beans.ConstructorProperties;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TokenDto {
  private final String refreshToken;
  private final UUID memberPublicId;
  private final String role;

  @ConstructorProperties({"refreshToken", "memberPublicId", "role"})
  public TokenDto(String refreshToken, UUID memberPublicId, String role) {
    this.refreshToken = refreshToken;
    this.memberPublicId = memberPublicId;
    this.role = role;
  }
}
