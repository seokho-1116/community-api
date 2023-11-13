package com.example.community.acceptance;

import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenAcceptanceTest extends AcceptanceTest {
  @DisplayName("사용자_리프레시_토큰으로_액세스_토큰을_갱신한다")
  @Test
  void refreshToken() {
    LoginRequest loginRequest = new LoginRequest(TEST_DATA.getMemberSignupId(),
        TEST_DATA.getMemberSignupPassword());
    TokenResponse loginResponse = RestAssuredResponseFactory.getLoginResponse(loginRequest);
    TokenRefreshRequest request = new TokenRefreshRequest(loginResponse.getRefreshTokenPublicId());

    TokenResponse tokenResponse =RestAssuredResponseFactory
        .getTokenRefreshResponse(request);

    SoftAssertions.assertSoftly(softly -> {
      softly.assertThat(tokenResponse.getAccessToken()).isNotEmpty();
      softly.assertThat(tokenResponse.getExpiresIn()).isNotEmpty();
      softly.assertThat(tokenResponse.getRefreshTokenPublicId())
          .isNotEqualTo(request.getRefreshTokenPublicId());
    });
  }

  @DisplayName("이미_갱신한_사용자_리프레시_토큰으로_액세스_토큰을_갱신한다")
  @Test
  void refreshTokenWithAlreadyRefreshed() {
    LoginRequest loginRequest = new LoginRequest(TEST_DATA.getMemberSignupId(),
        TEST_DATA.getMemberSignupPassword());
    TokenResponse loginResponse = RestAssuredResponseFactory.getLoginResponse(loginRequest);
    TokenRefreshRequest request = new TokenRefreshRequest(loginResponse.getRefreshTokenPublicId());
    RestAssuredResponseFactory.getTokenRefreshResponse(request);

    Assertions.assertThatThrownBy(() -> RestAssuredResponseFactory
        .getTokenRefreshResponse(request));
  }
}
