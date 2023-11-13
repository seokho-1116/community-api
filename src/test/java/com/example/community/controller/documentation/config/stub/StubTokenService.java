package com.example.community.controller.documentation.config.stub;

import com.example.community.repository.TokenJpaRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import com.example.community.service.TokenService;
import com.example.community.service.dto.TokenResponseDto;
import java.util.UUID;

public class StubTokenService extends TokenService {
  public StubTokenService(JwtFactory jwtFactory, TokenJpaRepository tokenJpaRepository) {
    super(jwtFactory, tokenJpaRepository);
  }

  @Override
  public  TokenResponseDto refresh(UUID publicId)
      throws InvalidTokenException
  {
    return TokenResponseDto.create("", UUID.randomUUID(), "");
  }

  @Override
  public TokenResponseDto createToken(UUID memberPublicId, String role) {
    return TokenResponseDto.create("", UUID.randomUUID(), "");
  }
}
