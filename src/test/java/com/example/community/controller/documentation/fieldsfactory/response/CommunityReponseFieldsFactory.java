package com.example.community.controller.documentation.fieldsfactory.response;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class CommunityReponseFieldsFactory {
  public static List<FieldDescriptor> communityDetailResponseFields() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data.introduction").type(STRING).description("커뮤니티 소개"),
        fieldWithPath("data.companyInfo").type(STRING).description("커뮤니티 회사 소개"),
        fieldWithPath("data.contactInfo").type(STRING).description("연락처"),
        fieldWithPath("data.privacyPolicy").type(STRING).description("개인정보처리방침"),
        fieldWithPath("data.terms").type(STRING).description("이용약관"),
        fieldWithPath("data.adsInfo").type(STRING).description("광고 문의 정보")
    );
  }
}
