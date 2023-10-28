package com.example.community.service;

import com.example.community.repository.TokenJpaRepository;
import com.example.community.repository.TokenQueryRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.TokenType;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import com.example.community.service.dto.TokenDto;
import com.example.community.service.dto.TokenRefreshResponseDto;
import com.example.community.service.dto.TokenResponseDto;
import com.example.community.service.entity.Role;
import com.example.community.service.entity.Token;
import com.example.community.service.exception.TokenNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
  private final JwtFactory jwtFactory;
  private final TokenQueryRepository tokenQueryRepository;
  private final TokenJpaRepository tokenJpaRepository;

  public TokenRefreshResponseDto refresh(UUID publicId) throws InvalidTokenException {
    TokenDto dto = tokenQueryRepository.findTokenByPublicId(publicId)
        .orElseThrow(TokenNotFoundException::new);

    if (isNotValid(dto)) {
      throw new InvalidTokenException("refresh 토큰이 올바르지 않습니다.");
    }

    return TokenRefreshResponseDto.create(
        jwtFactory.createAccessToken(dto.getMemberPublicId(), dto.getRole()),
        jwtFactory.getExpiresIn());
  }

  private boolean isNotValid(TokenDto dto) {
    return !jwtFactory.isValid(TokenType.REFRESH, dto.getRefreshToken());
  }

  public TokenResponseDto createToken(String memberPublicId , String role) {
    Token token = Token.create(
        jwtFactory.createRefreshToken(memberPublicId, role),
        UUID.fromString(memberPublicId),
        Role.valueOf(role));

    tokenJpaRepository.save(token);

    return TokenResponseDto.create(
        jwtFactory.createAccessToken(memberPublicId, role),
        token.getPublicId(),
        jwtFactory.getExpiresIn());
  }
}
