package com.example.community.controller.documentation.fieldsfactory.response;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class PostResponseFieldsFactory {
  public static List<FieldDescriptor> pagePostsResponseFields() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data.totalElements").type(NUMBER).description("전체 게시글 개수"),
        fieldWithPath("data.totalPages").type(NUMBER).description("전체 게시글 페이지 개수"),
        fieldWithPath("data.number").type(NUMBER).description("현재 페이지 번호"),
        fieldWithPath("data.numberOfElements").type(NUMBER).description("현재 페이지의 게시글 개수"),
        fieldWithPath("data.hasNext").type(BOOLEAN).description("다음 페이지 존재 유무"),
        fieldWithPath("data.hasPrevious").description("이전 페이지 존재 유무"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.content").type(ARRAY).description("해당 페이지 게시글들"),
        fieldWithPath("data.content[0]").type(ARRAY).description("해당 페이지 게시글"),
        fieldWithPath("data.content[0].publicId").type(STRING).description("게시글 id"),
        fieldWithPath("data.content[0].title").type(STRING).description("게시글 제목"),
        fieldWithPath("data.content[0].content").type(STRING).description("게시글 내용"),
        fieldWithPath("data.content[0].nickname").type(STRING).description("작성자 닉네임"),
        fieldWithPath("data.content[0].viewsCount").type(NUMBER).description("게시글 조회 수"),
        fieldWithPath("data.content[0].boardId").type(STRING).description("게시판 id"),
        fieldWithPath("data.content[0].boardCategory").type(STRING).description("게시판 카테고리"),
        fieldWithPath("data.content[0].postCategory").type(STRING).description("게시글 카테고리"),
        fieldWithPath("data.content[0].createdDate").type(STRING).description("게시글 생성일")
    );
  }

  public static List<FieldDescriptor> getPostDetailResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.publicId").type(STRING).description("해당 페이지 게시글"),
        fieldWithPath("data.title").type(STRING).description("게시글 제목"),
        fieldWithPath("data.content").type(STRING).description("게시글 내용"),
        fieldWithPath("data.nickname").type(STRING).description("작성자 닉네임"),
        fieldWithPath("data.createdDate").type(STRING).description("게시글 생성일"),
        fieldWithPath("data.viewsCount").type(NUMBER).description("게시글 조회 수"),
        fieldWithPath("data.upVotesCount").type(NUMBER).description("게시글 추천 수"),
        fieldWithPath("data.downVotesCount").type(NUMBER).description("게시글 비추천 수"),
        fieldWithPath("data.boardCategory").type(STRING).description("게시판 카테고리"),
        fieldWithPath("data.postCategory").type(STRING).description("게시글 카테고리"),
        fieldWithPath("data.postURL").type(STRING).description("게시글 URL"),
        fieldWithPath("data.isOwner").type(BOOLEAN).description("게시글 소유주 여부")
    );
  }

  public static List<FieldDescriptor> postCreateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.postId").type(STRING).description("생성된 페이지 게시글 id")
    );
  }

  public static List<FieldDescriptor> postUpdateResponseFields() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.postId").type(STRING).description("업데이트된 페이지 게시글 id")
    );
  }

  public static List<FieldDescriptor> getPostDeleteResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.postId").type(STRING).description("삭제된 페이지 게시글 id")
    );
  }
}
