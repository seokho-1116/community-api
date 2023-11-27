package com.example.community.service;

import static liquibase.repackaged.net.sf.jsqlparser.util.validation.metadata.NamedObject.role;

import com.example.community.repository.TokenJpaRepository;
import com.example.community.security.authentication.jwt.JwtFactory;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import com.example.community.service.dto.TokenResponseDto;
import com.example.community.service.entity.Role;
import com.example.community.service.entity.Token;
import com.example.community.service.exception.TokenNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
  private final JwtFactory jwtFactory;
  private final TokenJpaRepository tokenJpaRepository;

  @Transactional
  public TokenResponseDto refresh(UUID publicId) throws InvalidTokenException {
    Token token = tokenJpaRepository.findTokenByPublicId(publicId)
        .orElseThrow(TokenNotFoundException::new);

    if (isNotValid(token)) {
      throw new InvalidTokenException("refresh 토큰이 올바르지 않거나 권한이 없습니다.");
    }

    tokenJpaRepository.remove(token);

    Token newRefreshToken = getNewRefreshToken(token);

    tokenJpaRepository.save(newRefreshToken);

    return createTokenResponseDto(newRefreshToken);
  }

  private TokenResponseDto createTokenResponseDto(Token token) {
    return TokenResponseDto.create(
        jwtFactory.createAccessToken(
            token.getMemberPublicId().toString(),
            token.getRole().toString()
        ), token.getPublicId(), jwtFactory.getExpiresIn()
    );
  }

  private Token getNewRefreshToken(Token token) {
    return Token.create(jwtFactory.createRefreshToken(token.getMemberPublicId().toString(),
        token.getRole().toString()), token.getMemberPublicId(), token.getRole());
  }

  private boolean isNotValid(Token token) {
    return !jwtFactory.isValid(token.getRefreshToken());
  }

  @Transactional
  public TokenResponseDto createToken(UUID memberPublicId , String role) {
    Token token = Token.create(jwtFactory.createRefreshToken(memberPublicId.toString(), role),
        memberPublicId, Role.valueOf(role));

    tokenJpaRepository.save(token);

    return createTokenResponseDto(token);
  }
}
