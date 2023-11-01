package com.example.community.controller.response;

import com.example.community.service.dto.CommunityDetailResponseDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommunityDetailResponse {
  private final String introduction;
  private final String companyInfo;
  private final String contactInfo;
  private final String privacyPolicy;
  private final String terms;
  private final String adsInfo;

  @JsonCreator
  private CommunityDetailResponse(@JsonProperty("introduction") String introduction,
      @JsonProperty("companyInfo") String companyInfo,
      @JsonProperty("contactInfo") String contactInfo,
      @JsonProperty("privacyPolicy") String privacyPolicy, @JsonProperty("terms") String terms,
      @JsonProperty("adsInfo") String adsInfo) {
    this.introduction = introduction;
    this.companyInfo = companyInfo;
    this.contactInfo = contactInfo;
    this.privacyPolicy = privacyPolicy;
    this.terms = terms;
    this.adsInfo = adsInfo;
  }

  public static CommunityDetailResponse create(CommunityDetailResponseDto dto) {
    return new CommunityDetailResponse(dto.getIntroduction(), dto.getCompanyInfo(),
        dto.getContactInfo(), dto.getPrivacyPolicy(), dto.getTerms(), dto.getAdsInfo());
  }
}
