package com.example.community.controller.intergration;

import com.example.community.controller.response.CommunityDetailResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class CommunityIntegrationTest extends IntegrationTest {
  @DisplayName("현재 세팅된 커뮤니티_정보를_가져온다")
  @Test
  void getCommunityInfo() {
    ExtractableResponse<Response> response =  RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/community")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    CommunityDetailResponse communityResponse = changeResponseType(response, new TypeRef<>() {});

    SoftAssertions.assertSoftly(softly -> assertAllPropertiesNotEmpty(softly, communityResponse));
  }

  private void assertAllPropertiesNotEmpty(SoftAssertions softly,
      CommunityDetailResponse communityResponse) {
    softly.assertThat(communityResponse.getAdsInfo()).isNotEmpty();
    softly.assertThat(communityResponse.getCompanyInfo()).isNotEmpty();
    softly.assertThat(communityResponse.getContactInfo()).isNotEmpty();
    softly.assertThat(communityResponse.getTerms()).isNotEmpty();
    softly.assertThat(communityResponse.getContactInfo()).isNotEmpty();
    softly.assertThat(communityResponse.getIntroduction()).isNotEmpty();
  }
}
