package com.example.community.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.CommentService;
import com.example.community.service.dto.CommentDetailDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CommentController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class CommentControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private CommentService commentService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getComments() throws Exception {
    String boardId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postId = "4c063a22-5716-4012-a770-57299395ecdc";
    int size = 10;
    int page = 0;
    PageCommentRequest request = new PageCommentRequest(OffsetDateTime.now().toString(),
        page, size);

    when(commentService.findComments(anyString(), anyString(), any()))
        .thenReturn(createTestPage(size, size));

    mockMvc.perform(get("/api/boards/{board_id}/posts/{post_id}/comments", boardId,
            postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getPageCommentsResponseField())
        ));
  }

  @Test
  void createComment() throws Exception {
    String boardId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postId = "4c063a22-5716-4012-a770-57299395ecdc";
    UUID commentId = UUID.randomUUID();
    CommentCreateRequest request = new CommentCreateRequest(UUID.randomUUID(), "content");

    when(commentService.createComment(any())).thenReturn(commentId);

    mockMvc.perform(post("/api/boards/{board_id}/posts/{post_id}/comments", boardId,
        postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getCommentCreateResponseField())
        ));
  }

  @Test
  void updateComment() throws Exception {
    String boardId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postId = "4c063a22-5716-4012-a770-57299395ecdc";
    String commentId = "ba585502-4506-45b7-b38d-78d3d4314425";
    String content = "content";
    CommentUpdateRequest request = new CommentUpdateRequest(content);

    when(commentService.updateComment(any())).thenReturn(UUID.fromString(commentId));

    mockMvc.perform(patch("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
            boardId, postId, commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getCommentUpdateResponseField())
        ));
  }

  @Test
  void deleteComment() throws Exception {
    String boardId = "71239da8-8d81-41cf-9328-5e754d8e6c80";
    String postId = "4c063a22-5716-4012-a770-57299395ecdc";
    String commentId = "71239da8-8d81-41cf-9328-5e754d8e6c80";

    when(commentService.deleteComment(any(), any())).thenReturn(UUID.fromString(commentId));

    mockMvc.perform(delete("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
            boardId, postId, commentId))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getCommentDeleteResponseField())
        ));
  }

  private static Page<CommentDetailDto> createTestPage(int size, int total) {
    List<CommentDetailDto> content = IntStream.range(0, size)
        .mapToObj(d -> createTestComment())
        .collect(Collectors.toList());

    return new PageImpl<>(content, Pageable.ofSize(size), total);
  }

  private static CommentDetailDto createTestComment() {
    return new CommentDetailDto("nickname", "content", OffsetDateTime.now(),
        0, 0);
  }
}