package com.example.community.acceptance;

import com.example.community.controller.response.CommunityDetailResponse;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommunityAcceptanceTest extends AcceptanceTest {
  @DisplayName("현재 세팅된 커뮤니티_정보를_가져온다")
  @Test
  void getCommunityInfo() {
    CommunityDetailResponse communityResponse = RestAssuredResponseFactory.getCommunityResponse();

    SoftAssertions.assertSoftly(assertPropertiesOf(communityResponse));
  }

  private Consumer<SoftAssertions> assertPropertiesOf(
      CommunityDetailResponse communityResponse) {
    return softly -> {
      softly.assertThat(communityResponse.getAdsInfo()).isNotEmpty();
      softly.assertThat(communityResponse.getCompanyInfo()).isNotEmpty();
      softly.assertThat(communityResponse.getContactInfo()).isNotEmpty();
      softly.assertThat(communityResponse.getTerms()).isNotEmpty();
      softly.assertThat(communityResponse.getContactInfo()).isNotEmpty();
      softly.assertThat(communityResponse.getIntroduction()).isNotEmpty();
    };
  }
}
