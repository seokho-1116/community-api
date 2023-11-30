package com.example.community.controller.documentation.fieldsfactory.request;

import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class CommentRequestFieldsFactory {

  public static List<FieldDescriptor> pageCommentRequestFields() {
    return List.of(
        fieldWithPath("previousDate").type(STRING).description("마지막 댓글 생성 일자"),
        fieldWithPath("size").type(NUMBER).description("페이지 원소 크기")
    );
  }

  public static List<FieldDescriptor> commentCreateRequestFields() {
    return List.of(
        fieldWithPath("content").type(STRING).description("댓글 내용")
    );
  }

  public static List<FieldDescriptor> commentUpdateRequestFields() {
    return List.of(
        fieldWithPath("content").type(STRING).description("업데이트할 댓글 내용")
    );
  }
}
