package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static com.example.api.jooqgen.tables.Post.POST;
import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;
import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.PostSummaryDto;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@JooqTest
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class PostQueryRepositoryTest {
  @Autowired
  private DSLContext dslContext;

  @Test
  void selectPostsSummaryByPaging() {
    OffsetDateTime time = OffsetDateTime.of(2020, 6, 28,
        16, 26, 55, 0, ZoneOffset.ofHours(0));
    int size = 10;

    List<PostSummaryDto> dtos = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            BOARD.NAME, POST.POST_CATEGORY_ID, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.gt(time))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(size)
        .fetchInto(PostSummaryDto.class);

    assertThat(dtos).hasSize(size);
  }
}