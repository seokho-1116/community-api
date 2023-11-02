package com.example.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.service.dto.PostCategoryDto;
import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
class PostCategoryQueryRepositoryTest {
  private PostCategoryQueryRepository postCategoryQueryRepository;

  @Autowired
  public PostCategoryQueryRepositoryTest(DSLContext dslContext) {
    this.postCategoryQueryRepository = new PostCategoryQueryRepository(dslContext);
  }

  @Test
  void selectPostCategories() {
    UUID boardPublicId = UUID.fromString("8f712b3f-bdf2-4261-bacb-9d224b05a6e8");

    List<PostCategoryDto> categoryList = postCategoryQueryRepository.findPostCategoryByBoardPublicId(
        boardPublicId);

    assertThat(categoryList).isNotEmpty();
  }
}