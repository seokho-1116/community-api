package com.example.community.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.common.TestData.TestDataType;
import com.example.community.controller.request.CommentCreateRequest;
import com.example.community.controller.request.CommentUpdateRequest;
import com.example.community.controller.request.PageCommentRequest;
import com.example.community.controller.response.CommentCreateResponse;
import com.example.community.controller.response.CommentDeleteResponse;
import com.example.community.controller.response.CommentDetailResponse;
import com.example.community.controller.response.CommentUpdateResponse;
import com.example.community.controller.response.PageCommentResponse;
import com.example.community.security.authentication.login.request.LoginRequest;
import com.example.community.security.authentication.login.response.TokenResponse;
import java.time.OffsetDateTime;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentAcceptanceTest extends AcceptanceTest {
  @DisplayName("게시글의_댓글을_페이징해서_가져온다")
  @Test
  void getCommentsTest() {
    String boardPublicId = TEST_DATA.getCommentBoardPublicId(TestDataType.COMMON);
    String postPublicId = TEST_DATA.getCommentPostPublicId(TestDataType.COMMON);
    PageCommentRequest request = new PageCommentRequest(OffsetDateTime.now(), 20);

    PageCommentResponse pageCommentResponse = RestAssuredResponseFactory
        .getCommentsWithPagingResponse(request, boardPublicId, postPublicId);

    SoftAssertions.assertSoftly(assertPropertiesOf(pageCommentResponse));
  }

  private Consumer<SoftAssertions> assertPropertiesOf(
      PageCommentResponse pageCommentResponse) {
    return softly -> {
      softly.assertThat(pageCommentResponse).isNotNull();
      softly.assertThat(pageCommentResponse.getContent()).isNotEmpty();
      softly.assertThat(pageCommentResponse.getNumberOfElements()).isNotZero();
      softly.assertThat(pageCommentResponse.getTotalElements()).isNotZero();
      softly.assertThat(pageCommentResponse.getContent())
          .allSatisfy(assertAllCommentProperties());
    };
  }

  private ThrowingConsumer<CommentDetailResponse> assertAllCommentProperties() {
    return comment -> {
      assertThat(comment.getContent()).isNotEmpty();
      assertThat(comment.getNickname()).isNotEmpty();
      assertThat(comment.getCreatedDate()).isNotNull();
      assertThat(comment.isOwner()).isFalse();
    };
  }

  @DisplayName("로그인한_사용자가_댓글을_작성한다")
  @Test
  void createCommentTest() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getPostBoardPublicId(TestDataType.COMMON);
    String postPublicId = TEST_DATA.getPostPublicId(TestDataType.COMMON);
    CommentCreateRequest request = new CommentCreateRequest("content");

    CommentCreateResponse commentCreateResponse = RestAssuredResponseFactory
        .getCommentCreateResponse(tokenResponse.getAccessToken(), request, boardPublicId,
            postPublicId);

    assertThat(commentCreateResponse.getCommentId()).isNotNull();
  }

  private LoginRequest createLoginRequest() {
    return new LoginRequest(TEST_DATA.getMemberSignupId(),
        TEST_DATA.getMemberSignupPassword());
  }

  @DisplayName("사용자가_본인의_댓글을_업데이트한다")
  @Test
  void updateComment() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getCommentBoardPublicId(TestDataType.COMMON);
    String postPublicId = TEST_DATA.getCommentPostPublicId(TestDataType.COMMON);
    String commentPublicId = TEST_DATA.getCommentPublicId(TestDataType.FOR_UPDATE);
    CommentUpdateRequest request = new CommentUpdateRequest("new");

    CommentUpdateResponse commentUpdateResponse = RestAssuredResponseFactory
        .getCommentUpdateResponse(tokenResponse.getAccessToken(), request, boardPublicId,
            postPublicId, commentPublicId);

    assertThat(commentUpdateResponse.getCommentId()).isNotNull();
  }

  @DisplayName("사용자가_본인의_댓글을_삭제한다")
  @Test
  void deleteComment() {
    LoginRequest loginRequest = createLoginRequest();
    TokenResponse tokenResponse = RestAssuredResponseFactory
        .getLoginResponse(loginRequest);

    String boardPublicId = TEST_DATA.getCommentBoardPublicId(TestDataType.COMMON);
    String postPublicId = TEST_DATA.getCommentPostPublicId(TestDataType.COMMON);
    String commentPublicId = TEST_DATA.getCommentPublicId(TestDataType.FOR_UPDATE);

    CommentDeleteResponse commentDeleteResponse = RestAssuredResponseFactory
        .getCommentDeleteResponse(tokenResponse.getAccessToken(), boardPublicId, postPublicId,
            commentPublicId);

    assertThat(commentDeleteResponse.getCommentId()).isNotNull();
  }
}
