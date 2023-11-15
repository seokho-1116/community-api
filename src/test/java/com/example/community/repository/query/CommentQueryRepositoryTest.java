package com.example.community.repository.query;

import static org.assertj.core.api.Assertions.*;

import com.example.community.common.TestData.TestDataType;
import com.example.community.repository.CommentQueryRepository;
import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.PageCommentRequestDto;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class CommentQueryRepositoryTest extends QueryRepositoryTest {
  private final CommentQueryRepository commentQueryRepository;

  public CommentQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.commentQueryRepository = new CommentQueryRepository(dslContext);
  }

  @DisplayName("게시글에_댓글_페이징_조회_쿼리_테스트")
  @Test
  void findComments() {
    PageCommentRequestDto dto = createTestPageCommentRequestDto();

    Page<CommentDetailResponseDto> page = commentQueryRepository.findComments(dto);

    assertThat(page.getContent()).isNotEmpty();
  }

  private static PageCommentRequestDto createTestPageCommentRequestDto() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getCommentBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getCommentPostPublicId(TestDataType.COMMON));
    UUID memberPublicId = UUID.fromString(TEST_DATA.getCommentMemberPublicId(TestDataType.COMMON));
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    return PageCommentRequestDto.create(previousDate, size, boardPublicId, postPublicId,
        memberPublicId);
  }
}