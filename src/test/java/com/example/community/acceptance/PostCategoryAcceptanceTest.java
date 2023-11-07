package com.example.community.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.controller.response.BoardSummaryResponse;
import com.example.community.controller.response.PostCategoryResponse;
import java.util.List;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostCategoryAcceptanceTest extends AcceptanceTest {
  @DisplayName("게시판의_모든_카테고리를_출력한다")
  @Test
  void getPostCategoriesByBoardPublicId() {
    List<BoardSummaryResponse> boardSummaryResponseList =
        RestAssuredResponseFactory.getAllBoardResponse();

    List<PostCategoryResponse> postCategoryResponse = RestAssuredResponseFactory
        .getPostCategoryResponse(boardSummaryResponseList.get(0).getPublicId());

    assertThat(postCategoryResponse).isNotEmpty()
        .allSatisfy(assertPostCategoryProperties());
  }

  private ThrowingConsumer<PostCategoryResponse> assertPostCategoryProperties() {
    return postCategory -> {
      assertThat(postCategory.getPublicId()).isNotNull();
      assertThat(postCategory.getName()).isNotEmpty();
      assertThat(postCategory.getDescription()).isNotEmpty();
    };
  }
}
