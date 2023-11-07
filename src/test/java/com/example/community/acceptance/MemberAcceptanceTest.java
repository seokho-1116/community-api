package com.example.community.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.controller.request.EmailUpdateRequest;
import com.example.community.controller.request.NicknameUpdateRequest;
import com.example.community.controller.request.PasswordUpdateRequest;
import com.example.community.controller.request.SignupRequest;
import com.example.community.controller.response.EmailUpdateResponse;
import com.example.community.controller.response.MemberDetailResponse;
import com.example.community.controller.response.NicknameUpdateResponse;
import com.example.community.controller.response.SignupResponse;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class MemberAcceptanceTest extends AcceptanceTest {
  @DisplayName("사용자가_회원가입을_한다")
  @Test
  void signupTest() {
    SignupRequest request = createSignupRequest();

    SignupResponse signupResponse = RestAssuredResponseFactory
        .getSignupResponse(request);

    assertThat(signupResponse.getMemberId()).isNotNull();
  }

  private SignupRequest createSignupRequest() {
    return new SignupRequest("test", "nickname", "password",
        "test@email.com");
  }

  @DisplayName("사용자가_로그인을_한다")
  @Test
  void loginTest() {
    LoginRequest loginRequest = createLoginRequest();

    TokenResponse tokenResponse = RestAssuredResponseFactory.getLoginResponse(loginRequest);

    SoftAssertions.assertSoftly(softly -> {
      softly.assertThat(tokenResponse.getAccessToken()).isNotEmpty();
      softly.assertThat(tokenResponse.getExpiresIn()).isNotEmpty();
      softly.assertThat(tokenResponse.getRefreshTokenPublicId()).isNotNull();
    });
  }

  private LoginRequest createLoginRequest() {
    return new LoginRequest(TEST_DATA.getMemberSignupId(),
        TEST_DATA.getMemberSignupPassword());
  }

  @DisplayName("사용자가_회원정보를_조회한다")
  @Test
  void getMember() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory.getLoginResponse(loginRequest);

    MemberDetailResponse memberDetailResponse = RestAssuredResponseFactory
        .getMemberDetailResponse(tokenResponse.getAccessToken());

    SoftAssertions.assertSoftly(softly -> {
      softly.assertThat(memberDetailResponse.getNickname()).isNotEmpty();
      softly.assertThat(memberDetailResponse.getEmail()).isNotEmpty();
      softly.assertThat(memberDetailResponse.getCreatedDate()).isNotNull();
      softly.assertThat(memberDetailResponse.getSignupId()).isNotEmpty();
    });
  }

  @DisplayName("사용자가_사용자의_이메일을_업데이트한다")
  @Test
  void updateEmail() {
    TokenResponse tokenResponse = RestAssuredResponseFactory.getLoginResponse(createLoginRequest());
    EmailUpdateRequest request = createEmailUpdateRequest();

    EmailUpdateResponse emailUpdateResponse = RestAssuredResponseFactory
        .getEmailUpdateResponse(tokenResponse.getAccessToken(), request);

    assertThat(emailUpdateResponse.getEmail()).isEqualTo(request.getEmail());
  }

  private EmailUpdateRequest createEmailUpdateRequest() {
    return new EmailUpdateRequest("new@email.com");
  }

  @DisplayName("사용자가_사용자의_닉네임을_업데이트한다")
  @Test
  void updateNickname() {
    TokenResponse tokenResponse = RestAssuredResponseFactory.getLoginResponse(createLoginRequest());
    NicknameUpdateRequest request = createNicknameUpdateRequest();

    NicknameUpdateResponse nicknameUpdateResponse = RestAssuredResponseFactory
        .getNicknameUpdateResponse(tokenResponse.getAccessToken(), request);

    assertThat(nicknameUpdateResponse.getNickname()).isEqualTo(request.getNickname());
  }

  private NicknameUpdateRequest createNicknameUpdateRequest() {
    return new NicknameUpdateRequest("NewNickname");
  }

  @DisplayName("사용자가_사용자의_비밀번호를_업데이트한다")
  @Test
  void updatePassword() {
    TokenResponse tokenResponse = RestAssuredResponseFactory.getLoginResponse(createLoginRequest());
    PasswordUpdateRequest request = createPasswordUpdateRequest();

    RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", tokenResponse.getAccessToken())
        .body(request)
        .when().patch("/api/me/password")
        .then().log().all()
        .statusCode(HttpStatus.NO_CONTENT.value());
  }

  private PasswordUpdateRequest createPasswordUpdateRequest() {
    return new PasswordUpdateRequest("newPassword");
  }
}
