package com.example.community.acceptance;

import static org.assertj.core.api.Assertions.*;

import com.example.community.controller.response.BoardDetailResponse;
import com.example.community.controller.response.BoardSummaryResponse;
import java.util.List;
import java.util.function.Consumer;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardAcceptanceTest extends AcceptanceTest {
  @DisplayName("모든_게시판을_가져온다")
  @Test
  void getAllBoards() {
    List<BoardSummaryResponse> boardSummaryResponseList = RestAssuredResponseFactory
        .getAllBoardResponse();

    assertThat(boardSummaryResponseList).isNotEmpty()
        .allSatisfy(assertBoardProperties());
  }

  private ThrowingConsumer<BoardSummaryResponse> assertBoardProperties() {
    return board -> {
      assertThat(board.getName()).isNotEmpty();
      assertThat(board.getPublicId()).isNotEmpty();
    };
  }

  @DisplayName("게시판의_상세_정보를_가져온다")
  @Test
  void getBoardByPublicId() {
    String boardPublicId = TEST_DATA.getBoardPublicId();

    BoardDetailResponse boardResponse = RestAssuredResponseFactory
        .getBoardDetailResponse(boardPublicId);

    SoftAssertions.assertSoftly(assertPropertiesOf(boardResponse));
  }

  private Consumer<SoftAssertions> assertPropertiesOf(BoardDetailResponse boardResponse) {
    return softly -> {
      softly.assertThat(boardResponse.getCreatedDate()).isNotNull();
      softly.assertThat(boardResponse.getPublicId()).isNotEmpty();
      softly.assertThat(boardResponse.getDescription()).isNotEmpty();
      softly.assertThat(boardResponse.getName()).isNotEmpty();
    };
  }
}
