package com.example.community.acceptance;

import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.controller.response.TokenRefreshResponse;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenAcceptanceTest extends AcceptanceTest {
  @DisplayName("사용자_리프레시_토큰으로_액세스_토큰_갱신한다")
  @Test
  void refreshToken() {
    TokenRefreshRequest request = new TokenRefreshRequest(
        UUID.fromString(TEST_DATA.getTokenPublicId()));

    TokenRefreshResponse tokenRefreshResponse =RestAssuredResponseFactory
        .getTokenRefreshResponse(request);

    SoftAssertions.assertSoftly(softly -> {
      softly.assertThat(tokenRefreshResponse.getAccessToken()).isNotEmpty();
      softly.assertThat(tokenRefreshResponse.getExpiresIn()).isNotEmpty();
    });
  }
}
