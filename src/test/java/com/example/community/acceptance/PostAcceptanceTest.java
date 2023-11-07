package com.example.community.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.common.TestData.TestDataType;
import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.response.PagePostResponse;
import com.example.community.controller.response.PostCategoryResponse;
import com.example.community.controller.response.PostCreateResponse;
import com.example.community.controller.response.PostDeleteResponse;
import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.controller.response.PostUpdateResponse;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostAcceptanceTest extends AcceptanceTest {
  @DisplayName("모든_게시글을_페이징해서_가져온다")
  @Test
  void getPosts() {
    PagePostRequest request = createTestPagePostRequest();

    PagePostResponse pagePostResponse = RestAssuredResponseFactory
        .getPostsWithPagingResponse(request);

    SoftAssertions.assertSoftly(assertPropertiesOf(pagePostResponse));
  }

  @DisplayName("게시판의_모든_게시글을_페이징해서_가져온다")
  @Test
  void getPostsByBoardId() {
    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.COMMON);
    PagePostRequest request = createTestPagePostRequest();

    PagePostResponse pagePostResponse = RestAssuredResponseFactory
        .getPostsByBoardIdWithPagingResponse(request, boardPublicId);

    SoftAssertions.assertSoftly(assertPropertiesOf(pagePostResponse));
  }

  @DisplayName("익명의_사용자가_게시판의_게시글을_가져온다")
  @Test
  void getBoardPostByPostIdWithAnonymous() {
    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.COMMON);
    String postPublicId = TEST_DATA.getPostPublicId(TestDataType.COMMON);

    PostDetailResponse postDetailResponse = RestAssuredResponseFactory
        .getBoardPostByPostIdResponse(boardPublicId, postPublicId);

    SoftAssertions.assertSoftly(assertPropertiesOf(postDetailResponse, Boolean.FALSE));
  }

  @DisplayName("로그인한_사용자가_게시판의_본인_게시글을_가져온다")
  @Test
  void getBoardPostByPostIdWithMember() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.MY);
    String postPublicId = TEST_DATA.getPostPublicId(TestDataType.MY);

    PostDetailResponse postDetailResponse = RestAssuredResponseFactory
        .getBoardPostByPostIdWithAuthResponse(tokenResponse.getAccessToken(), boardPublicId,
            postPublicId);

    SoftAssertions.assertSoftly(assertPropertiesOf(postDetailResponse, Boolean.TRUE));
  }

  @DisplayName("로그인한_사용자가_게시글을_생성한다")
  @Test
  void createPost() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.COMMON);
    List<PostCategoryResponse> postCategoryResponse = RestAssuredResponseFactory
        .getPostCategoryResponse(boardPublicId);

    PostCreateRequest request = createPostCreateRequest(postCategoryResponse);
    PostCreateResponse postCreateResponse = RestAssuredResponseFactory
        .getPostCreateResponse(tokenResponse.getAccessToken(), request, boardPublicId);

    assertThat(postCreateResponse.getPostId()).isNotNull();
  }

  private static PostCreateRequest createPostCreateRequest(
      List<PostCategoryResponse> postCategoryResponse)
  {
    return new PostCreateRequest("title", "content",
        postCategoryResponse.get(0).getPublicId());
  }

  private LoginRequest createLoginRequest() {
    return new LoginRequest(TEST_DATA.getMemberSignupId(),
        TEST_DATA.getMemberSignupPassword());
  }

  @DisplayName("로그인한_사용자가_본인_게시글을_업데이트한다")
  @Test
  void updatePost() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.MY);
    String postPublicId = TEST_DATA.getPostPublicId(TestDataType.MY);
    PostUpdateRequest request = createUpdateRequest();

    PostUpdateResponse postUpdateResponse = RestAssuredResponseFactory
        .getPostUpdateResponse(tokenResponse.getAccessToken(), request, boardPublicId,
            postPublicId);

    assertThat(postUpdateResponse.getPostId()).isNotNull();
  }

  private static PostUpdateRequest createUpdateRequest() {
    return new PostUpdateRequest("title", "content");
  }

  @DisplayName("로그인한_사용자가_본인_게시글을_삭제한다")
  @Test
  void deletePost() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.MY);
    String postPublicId = TEST_DATA.getPostPublicId(TestDataType.MY);

    PostDeleteResponse postDeleteResponse = RestAssuredResponseFactory
        .getPostDeleteResponse(tokenResponse.getAccessToken(), boardPublicId, postPublicId);

    assertThat(postDeleteResponse.getPostId()).isNotNull();
  }

  private Consumer<SoftAssertions> assertPropertiesOf(PostDetailResponse postDetailResponse,
      Boolean isOwner) {
    return softly -> {
      softly.assertThat(postDetailResponse.getPostURL()).isNotEmpty();
      softly.assertThat(postDetailResponse.getPostCategory()).isNotEmpty();
      softly.assertThat(postDetailResponse.getContent()).isNotEmpty();
      softly.assertThat(postDetailResponse.getBoardCategory()).isNotEmpty();
      softly.assertThat(postDetailResponse.getCreatedDate()).isNotNull();
      softly.assertThat(postDetailResponse.getNickname()).isNotEmpty();
      softly.assertThat(postDetailResponse.getTitle()).isNotEmpty();
      softly.assertThat(postDetailResponse.getPublicId()).isNotEmpty();
      softly.assertThat(postDetailResponse.isOwner()).isEqualTo(isOwner);
    };
  }

  private PagePostRequest createTestPagePostRequest() {
    return new PagePostRequest(OffsetDateTime.now(), 20);
  }

  private Consumer<SoftAssertions> assertPropertiesOf(PagePostResponse pagePostResponse) {
    return softly -> {
      softly.assertThat(pagePostResponse).isNotNull();
      softly.assertThat(pagePostResponse.getContent()).isNotEmpty();
      softly.assertThat(pagePostResponse.getTotalPages()).isNotZero();
      softly.assertThat(pagePostResponse.getTotalElements()).isNotZero();
      softly.assertThat(pagePostResponse.getNumberOfElements()).isNotZero();
      softly.assertThat(pagePostResponse.getContent())
          .allSatisfy(assertAllPostProperties());
    };
  }

  private ThrowingConsumer<PostSummaryResponse> assertAllPostProperties() {
    return post -> {
      assertThat(post.getPostCategory()).isNotEmpty();
      assertThat(post.getNickname()).isNotEmpty();
      assertThat(post.getTitle()).isNotEmpty();
      assertThat(post.getPublicId()).isNotEmpty();
      assertThat(post.getContent()).isNotEmpty();
      assertThat(post.getBoardId()).isNotEmpty();
      assertThat(post.getBoardCategory()).isNotEmpty();
      assertThat(post.getCreatedDate()).isNotNull();
    };
  }
}
