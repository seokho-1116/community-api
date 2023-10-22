package com.example.community.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.PostCategoryDto;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.data.domain.Page;

@JooqTest
class PostQueryRepositoryTest {
  private final PostQueryRepository postQueryRepository;

  public PostQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.postQueryRepository = new PostQueryRepository(dslContext);
  }

  @Test
  void selectPagePostSummary() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    Page<PostSummaryResponseDto> page = postQueryRepository.findPosts(previousDate, size);

    assertThat(page).hasSize(size);
  }

  @Test
  void selectPagePostSummaryByBoardId() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;
    UUID boardPublicId = UUID.fromString("8f712b3f-bdf2-4261-bacb-9d224b05a6e8");

    Page<PostSummaryResponseDto> page = postQueryRepository.findPostsByBoardPublicId(boardPublicId,
        previousDate, size);

    assertThat(page).hasSize(size);
  }

  @Test
  void selectBoardPostDetailByPostId() {
    UUID boardPublicId = UUID.fromString("8f712b3f-bdf2-4261-bacb-9d224b05a6e8");
    UUID postPublicId = UUID.fromString("75305692-3c7b-4875-95b7-56f275b65c24");

    Optional<PostDetailResponseDto> dto = postQueryRepository.findPostByBoardPublicIdAndPublicId(
        boardPublicId, postPublicId);

    assertThat(dto).isPresent();
  }

  @Test
  void selectPostCategories() {
    UUID boardPublicId = UUID.fromString("8f712b3f-bdf2-4261-bacb-9d224b05a6e8");

    List<PostCategoryDto> categoryList = postQueryRepository.findPostCategoryByBoardPublicId(
        boardPublicId);

    assertThat(categoryList).isNotEmpty();
  }
}