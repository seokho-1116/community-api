package com.example.community.controller.documentation.fieldsfactory.request;

import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class TokenRequestFieldsFactory {

  public static List<FieldDescriptor> tokenRefreshRequestFields() {
    return List.of(
        fieldWithPath("refreshTokenPublicId").type(STRING).description("리프레스 토큰 퍼블릭 id")
    );
  }
}
