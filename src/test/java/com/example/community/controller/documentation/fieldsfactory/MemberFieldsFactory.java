package com.example.community.controller.documentation.fieldsfactory;

import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class MemberFieldsFactory {
  public static List<FieldDescriptor> getMemberCreateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.memberId").type(STRING).description("생성된 유저 id")
    );
  }

  public static List<FieldDescriptor> getMemberDetailResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.signupId").type(STRING).description("유저 로그인 id"),
        fieldWithPath("data.nickname").type(STRING).description("유저 닉네임"),
        fieldWithPath("data.email").type(STRING).description("유저 이메일"),
        fieldWithPath("data.createdDate").type(STRING).description("유저 생성일")
    );
  }

  public static List<FieldDescriptor> getMemberEmailUpdateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.email").type(STRING).description("업데이트 된 이메일 값")
    );
  }

  public static List<FieldDescriptor> getMemberPasswordUpdateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(STRING).description("성공 시 빈 문자열 반환")
    );
  }

  public static List<FieldDescriptor> getMemberNicknameUpdateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.nickname").type(STRING).description("업데이트 된 닉네임 값")
    );
  }

  public static List<FieldDescriptor> getLoginSuccessResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
        fieldWithPath("data.refreshTokenPublicId").type(STRING).description("리프레시 토큰 public id"),
        fieldWithPath("data.expiresIn").type(STRING).description("액세스 토큰 만료 시간")
    );
  }
}
