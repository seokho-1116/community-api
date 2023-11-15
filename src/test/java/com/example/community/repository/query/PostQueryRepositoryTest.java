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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

class PostQueryRepositoryTest extends QueryRepositoryTest {
  private final PostQueryRepository postQueryRepository;

  public PostQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.postQueryRepository = new PostQueryRepository(dslContext);
  }

  @DisplayName("게시글_페이징_조회_쿼리_테스트")
  @Test
  void findPosts() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    Page<PostSummaryResponseDto> page = postQueryRepository.findPosts(previousDate, size);

    assertThat(page).hasSize(size);
  }

  @DisplayName("게시판_공개_키로_게시글_페이징_조회_쿼리_테스트")
  @Test
  void findPostsByBoardPublicId() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));

    Page<PostSummaryResponseDto> page = postQueryRepository.findPostsByBoardPublicId(boardPublicId,
        previousDate, size);

    assertThat(page).hasSize(size);
  }

  @DisplayName("게시판_공개_키와_게시글_공개_키로_게시글_상세_조회_쿼리_테스트")
  @Test
  void findPostByBoardPublicIdAndPublicId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getPostPublicId(TestDataType.COMMON));

    Optional<PostDetailResponseDto> dto = postQueryRepository.findPostByBoardPublicIdAndPublicId(
        boardPublicId, postPublicId);

    assertThat(dto).isPresent();
  }
}