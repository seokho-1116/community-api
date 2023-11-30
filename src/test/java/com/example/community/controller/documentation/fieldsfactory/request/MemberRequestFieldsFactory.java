package com.example.community.controller.documentation.fieldsfactory.request;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class MemberRequestFieldsFactory {

  public static List<FieldDescriptor> memberCreateResponseFields() {
    return List.of(
      fieldWithPath("id").type(STRING).description("사용자 id"),
      fieldWithPath("password").type(STRING).description("사용자 패스워드"),
      fieldWithPath("nickname").type(STRING).description("사용자 닉네임"),
      fieldWithPath("email").type(STRING).description("사용자 이메일")
    );
  }

  public static List<FieldDescriptor> memberEmailUpdateRequestFields() {
    return List.of(
        fieldWithPath("email").type(STRING).description("업데이트 할 사용자 이메일")
    );
  }

  public static List<FieldDescriptor> memberNicknameUpdateRequestFields() {
    return List.of(
        fieldWithPath("nickname").type(STRING).description("업데이트 할 사용자 닉네임")
    );
  }

  public static List<FieldDescriptor> memberPasswordUpdateRequestFields() {
    return List.of(
        fieldWithPath("password").type(STRING).description("사용자 패스워드")
    );
  }

  public static List<FieldDescriptor> memberLoginRequestFields() {
    return List.of(
        fieldWithPath("signupId").type(STRING).description("로그인 할 사용자 id"),
        fieldWithPath("signupPassword").type(STRING).description("로그인 할 사용자 패스워드")
    );
  }
}
