package com.example.community.controller.documentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.community.controller.PostController;
import com.example.community.controller.documentation.fieldsfactory.request.PostRequestFieldsFactory;
import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.documentation.fieldsfactory.response.PostResponseFieldsFactory;
import com.example.community.service.PostService;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@WebMvcTest(controllers = PostController.class)
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class PostControllerTest extends RestDocsTestSetup {
  private static final String DUMMY_JWT = "Bearer token";
  private static final long TOTAL_POST = 50L;

  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("게시글_페이징_조회_문서_테스트")
  @Test
  void getPosts() throws Exception {
    //given
    PagePostRequest request = createTestPageRequest();

    Mockito.when(postService.findPosts(any(), anyInt()))
        .thenReturn(createTestPage(request.getSize()));

    //when
    ResultActions response = mockMvc.perform(get("/api/boards/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            requestFields(PostRequestFieldsFactory.pagePostsRequestFields()),
            responseFields(PostResponseFieldsFactory.pagePostsResponseFields())
        ));
  }

  @DisplayName("게시판_공개_키로_게시글_페이징_조회_문서_테스트")
  @Test
  void getPostsByBoardId() throws Exception {
    //given
    PagePostRequest request = createTestPageRequest();
    String boardPublicId = "cea61637-e18d-4919-bea2-ef0f9ad28010";

    Mockito.when(postService.findPostsByBoardPublicId(any(), any(), anyInt()))
        .thenReturn(createTestPage(request.getSize()));

    //when
    ResultActions response = mockMvc.perform(get("/api/boards/{board_id}/posts",
        boardPublicId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(parameterWithName("board_id").description("게시판 id")),
            requestFields(PostRequestFieldsFactory.pagePostsRequestFields()),
            responseFields(PostResponseFieldsFactory.pagePostsResponseFields())
        ));
  }

  @DisplayName("게시판_공개_키와_게시글_공개_키로_게시글_상세_정보_조회_문서_테스트")
  @Test
  void getBoardPostByPostId() throws Exception {
    //given
    String boardPublicId = "cea61637-e18d-4919-bea2-ef0f9ad28010";
    String postPublicId = "ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d";

    Mockito.when(postService.findBoardPostByPostId(any(), any(), any()))
        .thenReturn(createTestPostDetailDto());

    //when
    ResultActions response = mockMvc.perform(
        get("/api/boards/{board_id}/posts/{post_id}", boardPublicId,
            postPublicId)
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
            responseFields(PostResponseFieldsFactory.getPostDetailResponseField())
        ));
  }

  @DisplayName("게시글_생성_문서_테스트")
  @Test
  void createPost() throws Exception {
    //given
    String boardPublicId = "cea61637-e18d-4919-bea2-ef0f9ad28010";
    UUID postPublicId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");
    PostCreateRequest request = createTestPostCreateRequest();

    Mockito.when(postService.createNewPost(any())).thenReturn(postPublicId);

    //when
    ResultActions response = mockMvc.perform(post("/api/boards/{board_id}/posts", boardPublicId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .header("Authorization", DUMMY_JWT)
    );

    //then
    response
        .andExpect(status().isOk())
        .andDo(document.document(
            pathParameters(parameterWithName("board_id").description("게시판 id")),
            requestHeaders(headerWithName("Authorization").description("사용자 jwt 토큰")),
            requestFields(PostRequestFieldsFactory.postCreateRequestFields()),
            responseFields(PostResponseFieldsFactory.postCreateResponseField())
        ));
  }

  private static PostCreateRequest createTestPostCreateRequest() {
    return new PostCreateRequest("title", "content",
        UUID.fromString("2efa778a-8734-4b96-bf14-c75c4756888d"));
  }

  @DisplayName("게시글_업데이트_문서_테스트")
  @Test
  void updatePost() throws Exception {
    //given
    String boardPublicId = "cea61637-e18d-4919-bea2-ef0f9ad28010";
    UUID postPublicId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");
    PostUpdateRequest request = createTestPostUpdateRequest();

    Mockito.when(postService.updatePost(any())).thenReturn(postPublicId);

    //when
    ResultActions response = mockMvc.perform(
        patch("/api/boards/{board_id}/posts/{post_id}", boardPublicId,
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
            requestFields(PostRequestFieldsFactory.postUpdateRequestFields()),
            responseFields(PostResponseFieldsFactory.postUpdateResponseFields())
        ));
  }

  private static PostUpdateRequest createTestPostUpdateRequest() {
    return new PostUpdateRequest("title", "content");
  }

  @DisplayName("게시글_삭제_문서_테스트")
  @Test
  void deletePost() throws Exception {
    //given
    String boardPublicId = "cea61637-e18d-4919-bea2-ef0f9ad28010";
    UUID postPublicId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    Mockito.when(postService.deletePost(any(), any(), any())).thenReturn(postPublicId);

    //when
    ResultActions response = mockMvc.perform(
        delete("/api/boards/{board_id}/posts/{post_id}", boardPublicId,
            postPublicId)
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
            responseFields(PostResponseFieldsFactory.getPostDeleteResponseField())
        ));
  }

  private PagePostRequest createTestPageRequest() {
    return new PagePostRequest(OffsetDateTime.now(), 10);
  }

  private Page<PostSummaryResponseDto> createTestPage(int size) {
    List<PostSummaryResponseDto> content = IntStream.range(0, size)
        .mapToObj(PostControllerTest::createTestPostSummaryDto)
        .collect(Collectors.toList());

    return new PageImpl<>(content, Pageable.ofSize(size), TOTAL_POST);
  }

  private static PostSummaryResponseDto createTestPostSummaryDto(int number) {
    return new PostSummaryResponseDto(UUID.randomUUID().toString(), "title", "content",
        "user", number, "", "유머", "인기",
        OffsetDateTime.now());
  }

  private PostDetailResponseDto createTestPostDetailDto() {
    return new PostDetailResponseDto("id", "title", "content",
        "nickname", UUID.randomUUID(), OffsetDateTime.now(), 10,
        10, 10, "category", "category",
        "URL");
  }
}