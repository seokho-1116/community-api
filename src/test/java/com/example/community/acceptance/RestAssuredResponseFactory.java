package com.example.community.acceptance;

import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.EmailUpdateRequest;
import com.example.community.controller.request.NicknameUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.controller.request.PagePostRequest;
import com.example.community.controller.request.PostCreateRequest;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.controller.request.SignupRequest;
import com.example.community.controller.request.TokenRefreshRequest;
import com.example.community.controller.response.ApiResponse;
import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import com.example.community.controller.response.CommentCreateResponse;
import com.example.community.controller.response.CommentDeleteResponse;
import com.example.community.controller.response.CommentUpdateResponse;
import com.example.community.controller.response.CommunityDetailResponse;
import com.example.community.controller.response.EmailUpdateResponse;
import com.example.community.controller.response.MemberDetailResponse;
import com.example.community.controller.response.NicknameUpdateResponse;
import com.example.community.controller.response.PageCommentResponse;
import com.example.community.controller.response.PagePostResponse;
import com.example.community.controller.response.PostCategoryResponse;
import com.example.community.controller.response.PostCreateResponse;
import com.example.community.controller.response.PostDeleteResponse;
import com.example.community.controller.response.PostDetailResponse;
import com.example.community.controller.response.PostUpdateResponse;
import com.example.community.controller.response.SignupResponse;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestAssuredResponseFactory {
  public static CommunityDetailResponse getCommunityResponse() {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/community")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static BoardDetailResponse getBoardDetailResponse(String boardPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}", boardPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static List<BoardSummaryResponse> getAllBoardResponse() {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PagePostResponse getPostsWithPagingResponse(PagePostRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().get("/api/boards/posts")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PagePostResponse getPostsByBoardIdWithPagingResponse(
      PagePostRequest request, String boardPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().get("/api/boards/{board_id}/posts", boardPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PostDetailResponse getBoardPostByPostIdResponse(String boardPublicId,
      String postPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}/posts/{post_id}", boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static TokenResponse getLoginResponse(LoginRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().post("/api/me/login")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PostDetailResponse getBoardPostByPostIdWithAuthResponse(
      String accessToken, String boardPublicId, String postPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .when().get("/api/boards/{board_id}/posts/{post_id}", boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static List<PostCategoryResponse> getPostCategoryResponse(String boardPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}/posts/categories", boardPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PostCreateResponse getPostCreateResponse(String accessToken,
      PostCreateRequest request, String boardPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().post("/api/boards/{board_id}/posts/", boardPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PostUpdateResponse getPostUpdateResponse(String accessToken,
      PostUpdateRequest request, String boardPublicId, String postPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().patch("/api/boards/{board_id}/posts/{post_id}", boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PostDeleteResponse getPostDeleteResponse(String accessToken,
      String boardPublicId, String postPublicId)
  {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .when().delete("/api/boards/{board_id}/posts/{post_id}", boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static PageCommentResponse getCommentsWithPagingResponse(PageCommentRequest request,
      String boardPublicId, String postPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().get("/api/boards/{board_id}/posts/{post_id}/comments",
            boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static CommentCreateResponse getCommentCreateResponse(String accessToken,
      CommentCreateRequest request, String boardPublicId, String postPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().post("/api/boards/{board_id}/posts/{post_id}/comments",
            boardPublicId, postPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static CommentUpdateResponse getCommentUpdateResponse(String accessToken,
      CommentUpdateRequest request, String boardPublicId, String postPublicId,
      String commentPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().patch("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
            boardPublicId, postPublicId, commentPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static CommentDeleteResponse getCommentDeleteResponse(String accessToken,
      String boardPublicId, String postPublicId, String commentPublicId) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .when().delete("/api/boards/{board_id}/posts/{post_id}/comments/{comment_id}",
            boardPublicId, postPublicId, commentPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static SignupResponse getSignupResponse(SignupRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().post("/api/me/signup")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static MemberDetailResponse getMemberDetailResponse(String accessToken) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .when().get("/api/me")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static EmailUpdateResponse getEmailUpdateResponse(String accessToken,
      EmailUpdateRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().patch("/api/me/email")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static NicknameUpdateResponse getNicknameUpdateResponse(String accessToken,
      NicknameUpdateRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .header("Authorization", accessToken)
        .body(request)
        .when().patch("/api/me/nickname")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  public static TokenResponse getTokenRefreshResponse(TokenRefreshRequest request) {
    ExtractableResponse<Response> response = RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
        .when().post("/api/auth/token")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    return changeResponseType(response, new TypeRef<>() {});
  }

  private static <T> T changeResponseType(ExtractableResponse<Response> response,
      TypeRef<ApiResponse<T>> typeRef) {
    ApiResponse<T> apiResponse = response.as(typeRef);

    return apiResponse.getData();
  }
}
