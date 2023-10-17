package com.example.community.security.authentication.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class JwtFactoryTest {
  private JwtFactory jwtFactory;
  private JwtFactory expiredJwtFactory;

  public JwtFactoryTest() {
    this.jwtFactory = new JwtFactory(createJwtProperties(36000L));
    this.expiredJwtFactory = new JwtFactory(createJwtProperties(0L));
  }

  private JwtProperties createJwtProperties(long tokenValidityInMilliseconds) {
    return new JwtProperties("T".repeat(32), tokenValidityInMilliseconds,
        tokenValidityInMilliseconds);
  }

  @Test
  void createAccessTokenTest() {
    String memberId = "id";
    String role = "USER";

    String accessToken = jwtFactory.createAccessToken(memberId, role);

    assertThat(accessToken).isNotBlank();
  }

  @Test
  void createRefreshTokenTest() {
    String memberId = "id";
    String role = "USER";

    String refreshToken = jwtFactory.createRefreshToken(memberId, role);

    assertThat(refreshToken).isNotBlank();
  }

  @Test
  void getPayloadTestByAccessToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    String payload = jwtFactory.getPayload(TokenType.ACCESS, accessToken, "memberId");

    assertThat(payload).isEqualTo(memberId);
  }

  @Test
  void getPayloadTestByRefreshToken() {
    String memberId = "id";
    String role = "USER";
    String refreshToken = jwtFactory.createRefreshToken(memberId, role);

    String payload = jwtFactory.getPayload(TokenType.REFRESH, refreshToken, "memberId");

    assertThat(payload).isEqualTo(memberId);
  }

  @Test
  void getPayloadTestByUnValidClaimName() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    String payload = jwtFactory.getPayload(TokenType.ACCESS, accessToken, "un");

    assertThat(payload).isBlank();
  }

  @Test
  void isValidTestByValidToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    boolean isValid = jwtFactory.isValid(TokenType.ACCESS, accessToken);

    assertThat(isValid).isTrue();
  }

  @Test
  void isValidTestByUnValidToken() {
    String accessToken = "trash";

    boolean isValid = jwtFactory.isValid(TokenType.ACCESS, accessToken);

    assertThat(isValid).isFalse();
  }

  @Test
  void isValidTestByExpiredToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = expiredJwtFactory.createAccessToken(memberId, role);

    boolean isValid = jwtFactory.isValid(TokenType.ACCESS, accessToken);

    assertThat(isValid).isFalse();
  }
}