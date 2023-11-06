package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.repository.PostCategoryQueryRepository;
import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostCategoryQueryRepositoryTest extends QueryRepositoryTest {
  private final PostCategoryQueryRepository postCategoryQueryRepository;

  @Autowired
  public PostCategoryQueryRepositoryTest(DSLContext dslContext) {
    this.postCategoryQueryRepository = new PostCategoryQueryRepository(dslContext);
  }

  @Test
  void selectPostCategories() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getBoardPublicId());

    List<PostCategoryDto> categoryList = postCategoryQueryRepository.findPostCategoryByBoardPublicId(
        boardPublicId);

    assertThat(categoryList).isNotEmpty();
  }
}