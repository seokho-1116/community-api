package com.example.community.controller.documentation.fieldsfactory;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class BoardFieldsFactory {
  public static List<FieldDescriptor> getBoardSummaryResponseFields() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(ARRAY).description("응답 데이터"),
        fieldWithPath("data[0].publicId").type(STRING).description("게시판 id"),
        fieldWithPath("data[0].name").type(STRING).description("게시판 이름")
    );
  }

  public static List<FieldDescriptor> getBoardDetailResponseFields() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.publicId").type(STRING).description("게시판 id"),
        fieldWithPath("data.name").type(STRING).description("게시판 이름"),
        fieldWithPath("data.description").type(STRING).description("게시판 설명"),
        fieldWithPath("data.createdDate").type(STRING).description("게시판 생성일")
    );
  }
}
