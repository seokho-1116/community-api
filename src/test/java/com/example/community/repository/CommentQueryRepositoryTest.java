package com.example.community.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.CommentDetailResponseDto;
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
    UUID postId = UUID.fromString("5a836922-6bc9-4d1e-8f05-aad694d03a87");
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    Page<CommentDetailResponseDto> page = commentQueryRepository.findComments(postId,
        previousDate, size);

    assertThat(page.getContent()).hasSize(10);
  }
}