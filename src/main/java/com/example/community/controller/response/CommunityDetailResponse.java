package com.example.community.controller.response;

import com.example.community.service.dto.CommunityDetailResponseDto;
import lombok.Getter;

@Getter
public class CommunityDetailResponse {
  private final String introduction;
  private final String companyInfo;
  private final String contactInfo;
  private final String privacyPolicy;
  private final String terms;
  private final String adsInfo;

  private CommunityDetailResponse(String introduction, String companyInfo, String contactInfo,
      String privacyPolicy, String terms, String adsInfo) {
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
