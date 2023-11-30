package com.example.community.controller.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.CommentController;
import com.example.community.controller.documentation.fieldsfactory.request.CommentRequestFieldsFactory;
import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.controller.documentation.fieldsfactory.response.CommentResponseFieldsFactory;
import com.example.community.service.CommentService;
import com.example.community.service.dto.CommentDetailResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = CommentController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class CommentControllerTest extends RestDocsTestSetup {
  private static final String DUMMY_JWT = "Bearer token";

  @MockBean
  private CommentService commentService;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("댓글_페이징_조회_문서_테스트")
  @Test
  void getComments() throws Exception {
    //given
    String boardPublicId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postPublicId = "4c063a22-5716-4012-a770-57299395ecdc";
    int size = 10;
    PageCommentRequest request = createTestPageCommentRequest(size);

    when(commentService.findComments(any()))
        .thenReturn(createTestPage(size, size));

    //when
    ResultActions response = mockMvc.perform(
        get("/api/boards/{board_id}/posts/{post_id}/comments", boardPublicId,
            postPublicId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(
                parameterWithName("board_id").description("게시판 id"),
                parameterWithName("post_id").description("게시글 id")
            ),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰").optional()),
            requestFields(CommentRequestFieldsFactory.pageCommentRequestFields()),
            responseFields(CommentResponseFieldsFactory.pageCommentsResponseField())
        ));
  }

  private PageCommentRequest createTestPageCommentRequest(int size) {
    return new PageCommentRequest(OffsetDateTime.now(), size);
  }

  private Page<CommentDetailResponseDto> createTestPage(int size, int total) {
    List<CommentDetailResponseDto> content = IntStream.range(0, size)
        .mapToObj(d -> createTestComment())
        .collect(Collectors.toList());

    return new PageImpl<>(content, Pageable.ofSize(size), total);
  }

  private CommentDetailResponseDto createTestComment() {
    return new CommentDetailResponseDto(UUID.randomUUID(), "nickname", "content",
        OffsetDateTime.now(), 0, 0);
  }

  @DisplayName("댓글_생성_문서_테스트")
  @Test
  void createComment() throws Exception {
    //given
    String boardPublicId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postPublicId = "4c063a22-5716-4012-a770-57299395ecdc";
    UUID commentId = UUID.randomUUID();
    CommentCreateRequest request = createTestCreateRequest();

    when(commentService.createComment(any())).thenReturn(commentId);

    //when
    ResultActions response = mockMvc.perform(
        post("/api/boards/{board_id}/posts/{post_id}/comments", boardPublicId,
            postPublicId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(
                parameterWithName("board_id").description("게시판 id"),
                parameterWithName("post_id").description("게시글 id")
            ),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(CommentRequestFieldsFactory.commentCreateRequestFields()),
            responseFields(CommentResponseFieldsFactory.commentCreateResponseField())
        ));
  }

  private CommentCreateRequest createTestCreateRequest() {
    return new CommentCreateRequest("content");
  }

  @DisplayName("댓글_업데이트_문서_테스트")
  @Test
  void updateComment() throws Exception {
    //given
    String boardPublicId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postPublicId = "4c063a22-5716-4012-a770-57299395ecdc";
    String commentPublicId = "ba585502-4506-45b7-b38d-78d3d4314425";
    String content = "content";
    CommentUpdateRequest request = createTestCommentUpdateRequest(content);

    when(commentService.updateComment(any())).thenReturn(UUID.fromString(commentPublicId));

    //when
    ResultActions response = mockMvc.perform(
        patch("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
            boardPublicId, postPublicId, commentPublicId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(
                parameterWithName("board_id").description("게시판 id"),
                parameterWithName("post_id").description("게시글 id"),
                parameterWithName("comment_id").description("댓글 id")
            ),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(CommentRequestFieldsFactory.commentUpdateRequestFields()),
            responseFields(CommentResponseFieldsFactory.commentUpdateResponseField())
        ));
  }

  private CommentUpdateRequest createTestCommentUpdateRequest(String content) {
    return new CommentUpdateRequest(content);
  }

  @DisplayName("댓글_삭제_문서_테스트")
  @Test
  void deleteComment() throws Exception {
    //given
    String boardPublicId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postPublicId = "4c063a22-5716-4012-a770-57299395ecdc";
    String commentPublicId = "71239da8-8d81-41cf-9328-5e754d8e6c80";

    when(commentService.deleteComment(any())).thenReturn(UUID.fromString(commentPublicId));

    //when
    ResultActions response = mockMvc.perform(
            delete("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
                boardPublicId, postPublicId, commentPublicId)
                .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(
                parameterWithName("board_id").description("게시판 id"),
                parameterWithName("post_id").description("게시글 id"),
                parameterWithName("comment_id").description("댓글 id")
            ),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            responseFields(CommentResponseFieldsFactory.getCommentDeleteResponseField())
        ));
  }
}