package com.example.community.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.PageCommentRequestDto;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@JooqTest
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class CommentQueryRepositoryTest {
  private final CommentQueryRepository commentQueryRepository;

  public CommentQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.commentQueryRepository = new CommentQueryRepository(dslContext);
  }

  @Test
  void findComments() {
    PageCommentRequestDto dto = createTestPageCommentRequestDto();

    Page<CommentDetailResponseDto> page = commentQueryRepository.findComments(dto);

    assertThat(page.getContent()).isNotEmpty();
  }

  private static PageCommentRequestDto createTestPageCommentRequestDto() {
    UUID boardPublicId = UUID.fromString("0d0e35d5-4511-4f89-b42c-a81c86c948ac");
    UUID postPublicId = UUID.fromString("28362401-c59a-464e-8a64-0ab455464bc3");
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    return PageCommentRequestDto.create(previousDate, size, boardPublicId, postPublicId);
  }
}