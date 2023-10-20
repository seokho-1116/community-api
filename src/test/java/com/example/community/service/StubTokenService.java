package com.example.community.service;

import com.example.community.repository.TokenJpaRepository;
import com.example.community.repository.TokenQueryRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import com.example.community.service.dto.TokenRefreshResponseDto;
import com.example.community.service.dto.TokenResponseDto;
import java.util.UUID;

public class StubTokenService extends TokenService {
  public StubTokenService(JwtFactory jwtFactory, TokenQueryRepository tokenQueryRepository,
      TokenJpaRepository tokenJpaRepository) {
    super(jwtFactory, tokenQueryRepository, tokenJpaRepository);
  }

  @Override
  public TokenRefreshResponseDto refresh(UUID publicId) throws InvalidTokenException {
    return TokenRefreshResponseDto.create("", "");
  }

  @Override
  public TokenResponseDto createToken(String memberPublicId, String role) {
    return TokenResponseDto.create("", UUID.randomUUID(), "");
  }
}
