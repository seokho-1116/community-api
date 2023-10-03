package com.example.community.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.documentation.ResponseFieldsFactory;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostDetailDto;
import com.example.community.service.dto.PostSummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
@WebMvcTest(controllers = PostController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class PostControllerTest extends AbstractRestDocsControllerTest {
  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getPosts() throws Exception {
    long total = 50L;
    PagePostRequest request = new PagePostRequest(OffsetDateTime.now().toString(), 0, 10);

    Mockito.when(postService.findPosts(anyString(), any()))
        .thenReturn(createTestPage(request.getSize(), total));

    mockMvc.perform(get("/api/boards/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getPagePostsResponseField())
        ));
  }

  @Test
  void getPostsByBoardId() throws Exception {
    long total = 50L;
    PagePostRequest request = new PagePostRequest(OffsetDateTime.now().toString(), 0, 10);
    String boardId = "cea61637-e18d-4919-bea2-ef0f9ad28010";

    Mockito.when(postService.findPostsByBoardId(anyString(), anyString(), any()))
        .thenReturn(createTestPage(request.getSize(), total));

    mockMvc.perform(get("/api/boards/{board_id}/posts", boardId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getPagePostsResponseField()),
            pathParameters(parameterWithName("board_id").description("게시판 id"))
        ));
  }

  @Test
  void getBoardPostByPostId() throws Exception {
    String boardId = "cea61637-e18d-4919-bea2-ef0f9ad28010";
    String postId = "ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d";

    Mockito.when(postService.findBoardPostByPostId(anyString(), anyString()))
        .thenReturn(createTestPostDetailDto());

    mockMvc.perform(get("/api/boards/{board_id}/posts/{post_id}", boardId, postId))
        .andExpect(status().isOk())
        .andDo(document.document(
            responseFields(ResponseFieldsFactory.getPostDetailResponseField()),
            pathParameters(
                parameterWithName("board_id").description("게시판 id"),
                parameterWithName("post_id").description("게시글 id")
            )
        ));
  }

  private Page<PostSummaryDto> createTestPage(int size, long total) {
    List<PostSummaryDto> content = IntStream.range(0, size)
        .mapToObj(PostControllerTest::createTestPostSummaryDto)
        .collect(Collectors.toList());

    return new PageImpl<>(content, Pageable.ofSize(size), total);
  }

  private static PostSummaryDto createTestPostSummaryDto(int number) {
    return new PostSummaryDto(UUID.randomUUID().toString(), "title", "content",
        "user", number, "", "유머", "인기", OffsetDateTime.now());
  }

  private PostDetailDto createTestPostDetailDto() {
    return new PostDetailDto("id", "title", "content", "nickname",
        "author", OffsetDateTime.now(), 10, 10,
        10, "category", "category", "URL");
  }
}