package com.example.community.controller.documentation.fieldsfactory;

import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class TokenFieldsFactory {
  public static List<FieldDescriptor> getTokenRefreshResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.accessToken").type(STRING).description("액세스 토큰"),
        fieldWithPath("data.expiresIn").type(STRING).description("액세스 토큰 만료 시간")
    );
  }
}
