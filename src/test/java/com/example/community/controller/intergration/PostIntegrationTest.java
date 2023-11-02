package com.example.community.controller.intergration;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.response.PagePostResponse;
import com.example.community.controller.response.PostCategoryResponse;
import com.example.community.controller.response.PostCreateResponse;
import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostSummaryResponse;
import com.example.community.controller.response.PostUpdateResponse;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class PostIntegrationTest extends IntegrationTest {
  @DisplayName("모든_게시글을_가져온다")
  @Test
  void getPosts() {
    PagePostRequest request = createTestPagePostRequest();

    ExtractableResponse<Response> response =  RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().get("/api/boards/posts")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    PagePostResponse pagePostResponse = changeResponseType(response, new TypeRef<>() {});

    SoftAssertions.assertSoftly(assertPropertiesOf(pagePostResponse));
  }

  @DisplayName("게시판의_모든_게시글을_가져온다")
  @Test
  void getPostsByBoardId() {
    String boardId = "8f712b3f-bdf2-4261-bacb-9d224b05a6e8";
    PagePostRequest request = createTestPagePostRequest();

    ExtractableResponse<Response> response =  RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().get("/api/boards/{board_id}/posts", boardId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    PagePostResponse pagePostResponse = changeResponseType(response, new TypeRef<>() {});

    SoftAssertions.assertSoftly(assertPropertiesOf(pagePostResponse));
  }

  @DisplayName("게시판의_게시글을_가져온다")
  @Test
  void getBoardPostByPostId() {
    String boardId = "8f712b3f-bdf2-4261-bacb-9d224b05a6e8";
    String postId = "5d14b1e1-ad9a-4628-b12f-fee1ead86bda";

    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}/posts/{post_id}", boardId, postId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    PostDetailResponse postDetailResponse = changeResponseType(response, new TypeRef<>() {});

    SoftAssertions.assertSoftly(assertPropertiesOf(postDetailResponse));
  }

  @DisplayName("게시글을_생성한다")
  @Test
  void createPost() {
    LoginRequest loginRequest = new LoginRequest("seokho", "1234");
    ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(loginRequest)
        .when().post("/api/me/login")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();
    TokenResponse tokenResponse = changeResponseType(loginResponse, new TypeRef<>() {});

    String boardId = "8f712b3f-bdf2-4261-bacb-9d224b05a6e8";
    ExtractableResponse<Response> categoryResponse = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}/posts/categories", boardId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();
    List<PostCategoryResponse> postCategoryResponse = changeResponseType(categoryResponse,
        new TypeRef<>() {});

    PostCreateRequest request = new PostCreateRequest("title", "content",
        postCategoryResponse.get(0).getPublicId());
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", tokenResponse.getAccessToken())
        .body(request)
        .when().post("/api/boards/{board_id}/posts/", boardId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();
    PostCreateResponse postCreateResponse = changeResponseType(response, new TypeRef<>() {});

    assertThat(postCreateResponse.getPostId()).isNotNull();
  }

  @DisplayName("게시글을_업데이트한다")
  @Test
  void updatePost() {
    LoginRequest loginRequest = new LoginRequest("seokho", "1234");
    ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(loginRequest)
        .when().post("/api/me/login")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();
    TokenResponse tokenResponse = changeResponseType(loginResponse, new TypeRef<>() {});

    String boardId = "8f712b3f-bdf2-4261-bacb-9d224b05a6e8";
    String postId = "5d14b1e1-ad9a-4628-b12f-fee1ead86bda";
    PostUpdateRequest request = new PostUpdateRequest("title", "content");
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", tokenResponse.getAccessToken())
        .body(request)
        .when().patch("/api/boards/{board_id}/posts/{post_id}", boardId, postId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();
    PostUpdateResponse postUpdateResponse = changeResponseType(response, new TypeRef<>() {});

    assertThat(postUpdateResponse.getPostId()).isNotNull();
  }

  private Consumer<SoftAssertions> assertPropertiesOf(
      PostDetailResponse postDetailResponse) {
    return softly -> {
      softly.assertThat(postDetailResponse.getPostURL()).isNotEmpty();
      softly.assertThat(postDetailResponse.getPostCategory()).isNotEmpty();
      softly.assertThat(postDetailResponse.getContent()).isNotEmpty();
      softly.assertThat(postDetailResponse.getBoardCategory()).isNotEmpty();
      softly.assertThat(postDetailResponse.getCreatedDate()).isNotNull();
      softly.assertThat(postDetailResponse.getNickname()).isNotEmpty();
      softly.assertThat(postDetailResponse.getTitle()).isNotEmpty();
      softly.assertThat(postDetailResponse.getPublicId()).isNotEmpty();
      softly.assertThat(postDetailResponse.isOwner()).isFalse();
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
