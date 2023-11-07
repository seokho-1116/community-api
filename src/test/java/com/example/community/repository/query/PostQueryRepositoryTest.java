package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.*;

import com.example.community.common.TestData.TestDataType;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

class PostQueryRepositoryTest extends QueryRepositoryTest {
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
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));

    Page<PostSummaryResponseDto> page = postQueryRepository.findPostsByBoardPublicId(boardPublicId,
        previousDate, size);

    assertThat(page).hasSize(size);
  }

  @Test
  void selectBoardPostDetailByPostId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getPostPublicId(TestDataType.COMMON));

    Optional<PostDetailResponseDto> dto = postQueryRepository.findPostByBoardPublicIdAndPublicId(
        boardPublicId, postPublicId);

    assertThat(dto).isPresent();
  }
}