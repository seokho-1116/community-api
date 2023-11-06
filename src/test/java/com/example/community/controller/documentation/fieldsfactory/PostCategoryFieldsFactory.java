package com.example.community.controller.documentation.fieldsfactory;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class PostCategoryFieldsFactory {
  public static List<FieldDescriptor> getPostCategoryResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(ARRAY).description("응답 데이터"),
        fieldWithPath("data[0].publicId").type(STRING).description("게시글 public id"),
        fieldWithPath("data[0].name").type(STRING).description("게시글 카테고리 이름"),
        fieldWithPath("data[0].description").type(STRING).description("게시글 카테고리 설명")
    );
  }
}
