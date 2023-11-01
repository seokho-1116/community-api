package com.example.community.controller.intergration;

import static org.assertj.core.api.Assertions.*;

import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class BoardIntegrationTest extends IntegrationTest {
  @DisplayName("모든_게시판을_가져온다")
  @Test
  void getAllBoards() {
    ExtractableResponse<Response> response =  RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards")
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    List<BoardSummaryResponse> boardSummaryResponseList = changeResponseType(response,
        new TypeRef<>() {});

    assertThat(boardSummaryResponseList).isNotEmpty()
        .allSatisfy(this::assertAllPropertiesNotEmpty);
  }

  private void assertAllPropertiesNotEmpty(BoardSummaryResponse board) {
    assertThat(board.getName()).isNotEmpty();
    assertThat(board.getPublicId()).isNotEmpty();
  }

  @DisplayName("게시판의_상세_정보를_가져온다")
  @Test
  void getBoardByPublicId() {
    String boardPublicId = "f0e33ae7-1a7b-41e5-a7e3-691e2b07ffa3";

    ExtractableResponse<Response> response =  RestAssured.given().log().all()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when().get("/api/boards/{board_id}", boardPublicId)
        .then().log().all()
        .statusCode(HttpStatus.OK.value())
        .extract();

    BoardDetailResponse boardResponse = changeResponseType(response, new TypeRef<>() {});

    SoftAssertions.assertSoftly(softly -> assertAllPropertiesAreNotEmpty(softly, boardResponse));
  }

  private void assertAllPropertiesAreNotEmpty(SoftAssertions softly,
      BoardDetailResponse boardResponse) {
    softly.assertThat(boardResponse.getCreatedDate()).isNotNull();
    softly.assertThat(boardResponse.getPublicId()).isNotEmpty();
    softly.assertThat(boardResponse.getDescription()).isNotEmpty();
    softly.assertThat(boardResponse.getName()).isNotEmpty();
  }
}
