package com.example.community.documentation.fieldsfactory;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.List;
import org.springframework.restdocs.payload.FieldDescriptor;

public class CommentFieldsFactory {
  public static List<FieldDescriptor> getPageCommentsResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data.totalElements").type(NUMBER).description("전체 댓글 개수"),
        fieldWithPath("data.totalPages").type(NUMBER).description("전체 댓글 개수"),
        fieldWithPath("data.number").type(NUMBER).description("현재 페이지 번호"),
        fieldWithPath("data.numberOfElements").type(NUMBER).description("현재 페이지의 댓글 개수"),
        fieldWithPath("data.hasNext").type(BOOLEAN).description("다음 페이지 존재 유무"),
        fieldWithPath("data.hasPrevious").description("이전 페이지 존재 유무"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.content").type(ARRAY).description("해당 페이지 댓글들"),
        fieldWithPath("data.content[0]").type(ARRAY).description("해당 페이지 댓글"),
        fieldWithPath("data.content[0].nickname").type(STRING).description("작성자 닉네임"),
        fieldWithPath("data.content[0].content").type(STRING).description("댓글 내용"),
        fieldWithPath("data.content[0].createdDate").type(STRING).description("댓글 생성일"),
        fieldWithPath("data.content[0].upVotesCount").type(NUMBER).description("게시글 추천 수"),
        fieldWithPath("data.content[0].downVotesCount").type(NUMBER).description("게시글 비추천 수")
    );
  }

  public static List<FieldDescriptor> getCommentUpdateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.commentId").type(STRING).description("생성된 댓글 id")
    );
  }

  public static List<FieldDescriptor> getCommentDeleteResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.commentId").type(STRING).description("생성된 댓글 id")
    );
  }

  public static List<FieldDescriptor> getCommentCreateResponseField() {
    return List.of(
        fieldWithPath("message").type(STRING).description("응답 메시지 (정상: success)"),
        fieldWithPath("data").type(OBJECT).description("응답 데이터"),
        fieldWithPath("data.commentId").type(STRING).description("생성된 댓글 id")
    );
  }
}
