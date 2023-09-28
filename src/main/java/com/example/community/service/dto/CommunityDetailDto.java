package com.example.community.service.dto;

import lombok.Getter;

@Getter
public class CommunityDetailDto {
  private final String introduction;
  private final String companyInfo;
  private final String contactInfo;
  private final String privacyPolicy;
  private final String terms;
  private final String adsInfo;

  public CommunityDetailDto(String introduction, String companyInfo, String contactInfo,
      String privacyPolicy, String terms, String adsInfo) {
    this.introduction = introduction;
    this.companyInfo = companyInfo;
    this.contactInfo = contactInfo;
    this.privacyPolicy = privacyPolicy;
    this.terms = terms;
    this.adsInfo = adsInfo;
  }
}
