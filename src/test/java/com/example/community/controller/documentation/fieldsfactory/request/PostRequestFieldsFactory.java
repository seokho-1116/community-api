package com.example.community.controller.documentation.fieldsfactory.request;

import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class PostRequestFieldsFactory {

  public static List<FieldDescriptor> pagePostsRequestFields() {
    return List.of(
        fieldWithPath("previousDate").type(STRING).description("마지막 게시글 생성 일자"),
        fieldWithPath("size").type(NUMBER).description("페이지 원소 크기")
    );
  }

  public static List<FieldDescriptor> postCreateRequestFields() {
    return List.of(
        fieldWithPath("title").type(STRING).description("게시글 제목"),
        fieldWithPath("content").type(STRING).description("게시글 컨텐츠"),
        fieldWithPath("postCategoryPublicId").type(STRING).description("게시글 카테고리 퍼블릭 id")
    );
  }

  public static List<FieldDescriptor> postUpdateRequestFields() {
    return List.of(
        fieldWithPath("title").type(STRING).description("업데이트 할 게시글 제목"),
        fieldWithPath("content").type(STRING).description("업데이트 할 게시글 컨텐츠")
    );
  }
}
