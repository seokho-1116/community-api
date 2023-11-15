package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.PostCategoryQueryRepository;
import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostCategoryQueryRepositoryTest extends QueryRepositoryTest {
  private final PostCategoryQueryRepository postCategoryQueryRepository;

  @Autowired
  public PostCategoryQueryRepositoryTest(DSLContext dslContext) {
    this.postCategoryQueryRepository = new PostCategoryQueryRepository(dslContext);
  }

  @DisplayName("게시판_공개_키로_게시판_카테고리_조회_쿼리_테스트")
  @Test
  void findPostCategoryByBoardPublicId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getBoardPublicId());

    List<PostCategoryDto> categoryList = postCategoryQueryRepository.findPostCategoryByBoardPublicId(
        boardPublicId);

    assertThat(categoryList).isNotEmpty();
  }
}