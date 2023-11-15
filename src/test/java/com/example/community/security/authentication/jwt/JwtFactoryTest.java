package com.example.community.security.authentication.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
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

  @DisplayName("액세스_토큰_생성_테스트")
  @Test
  void createAccessTokenTest() {
    String memberId = "id";
    String role = "USER";

    String accessToken = jwtFactory.createAccessToken(memberId, role);

    assertThat(accessToken).isNotBlank();
  }

  @DisplayName("리프레시_토큰_생성_테스트")
  @Test
  void createRefreshTokenTest() {
    String memberId = "id";
    String role = "USER";

    String refreshToken = jwtFactory.createRefreshToken(memberId, role);

    assertThat(refreshToken).isNotBlank();
  }

  @DisplayName("액세스_토큰으로_페이로드_추출_테스트")
  @Test
  void getPayloadTestByAccessToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    String payload = jwtFactory.getPayload(accessToken, "memberId");

    assertThat(payload).isEqualTo(memberId);
  }

  @DisplayName("리프레시_토큰으로_페이로드_추출_테스트")
  @Test
  void getPayloadTestByRefreshToken() {
    String memberId = "id";
    String role = "USER";
    String refreshToken = jwtFactory.createRefreshToken(memberId, role);

    String payload = jwtFactory.getPayload(refreshToken, "memberId");

    assertThat(payload).isEqualTo(memberId);
  }

  @DisplayName("유효하지_않은_토큰으로_페이로드_추출_테스트")
  @Test
  void getPayloadTestByUnValidClaimName() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    String payload = jwtFactory.getPayload(accessToken, "un");

    assertThat(payload).isBlank();
  }

  @DisplayName("유효한_토큰으로_유효성_검증_테스트")
  @Test
  void isValidTestByValidToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = jwtFactory.createAccessToken(memberId, role);

    boolean isValid = jwtFactory.isValid(accessToken);

    assertThat(isValid).isTrue();
  }

  @DisplayName("유효하지_않은_토큰으로_유효성_검증_테스트")
  @Test
  void isValidTestByUnValidToken() {
    String accessToken = "trash";

    boolean isValid = jwtFactory.isValid(accessToken);

    assertThat(isValid).isFalse();
  }

  @DisplayName("만료된_토큰으로_유효성_검증_테스트")
  @Test
  void isValidTestByExpiredToken() {
    String memberId = "id";
    String role = "USER";
    String accessToken = expiredJwtFactory.createAccessToken(memberId, role);

    boolean isValid = jwtFactory.isValid(accessToken);

    assertThat(isValid).isFalse();
  }
}