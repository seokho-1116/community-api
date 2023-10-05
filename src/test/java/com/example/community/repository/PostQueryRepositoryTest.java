package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static com.example.api.jooqgen.tables.Post.POST;
import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;
import static org.assertj.core.api.Assertions.*;

import com.example.api.jooqgen.tables.Post;
import com.example.community.service.dto.PostCategoryDto;
import com.example.community.service.dto.PostDetailDto;
import com.example.community.service.dto.PostSummaryDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@JooqTest
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class PostQueryRepositoryTest {
  @Autowired
  private DSLContext dslContext;

  @Test
  void selectPagePostSummary() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    Pageable pageable = PageRequest.of(0, 10);

    List<PostSummaryDto> dtos = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            BOARD.PUBLIC_ID, BOARD.NAME, POST_CATEGORY.NAME, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.lt(previousDate))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(pageable.getPageSize())
        .fetchInto(PostSummaryDto.class);

    assertThat(dtos).hasSize(pageable.getPageSize());
  }

  @Test
  void selectPagePostSummaryByBoardId() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    Pageable pageable = PageRequest.of(0, 10);
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");

    List<PostSummaryDto> dtos = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            BOARD.PUBLIC_ID, BOARD.NAME, POST_CATEGORY.NAME, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.lt(previousDate)
            .and(POST.BOARD_ID.eq(boardId)))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(pageable.getPageSize())
        .fetchInto(PostSummaryDto.class);

    assertThat(dtos).hasSize(10);
  }

  @Test
  void selectBoardPostDetailByPostId() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");
    UUID postId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    PostDetailDto dto = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, MEMBER.PUBLIC_ID, MEMBER.NICKNAME,
            POST.CREATED_DATE, POST.VIEWS_COUNT, POST.UP_VOTES_COUNT, POST.DOWN_VOTES_COUNT,
            BOARD.NAME, POST_CATEGORY.NAME, POST.POST_URL)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .fetchOneInto(PostDetailDto.class);

    assertThat(dto).isNotNull();
  }

  @Test
  void updatePost() {
    String title = "update title";
    String content = "update content";
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");
    UUID postId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    dslContext
        .update(POST)
        .set(POST.TITLE, title)
        .set(POST.CONTENT, content)
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .execute();

    com.example.community.service.entity.Post post = findPost(boardId, postId)
        .orElseThrow();

    assertThat(post.getTitle()).isEqualTo(title);
    assertThat(post.getContent()).isEqualTo(content);
  }

  @Test
  void deletePost() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");
    UUID postId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    dslContext
        .delete(POST)
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .execute();

    assertThat(findPost(boardId, postId)).isNotPresent();
  }

  @Test
  void selectPostCategories() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");

    List<PostCategoryDto> categories = dslContext
        .select(POST_CATEGORY.NAME, POST_CATEGORY.DESCRIPTION)
        .from(POST_CATEGORY)
        .where(POST_CATEGORY.BOARD_ID.eq(boardId))
        .fetchInto(PostCategoryDto.class);

    assertThat(categories).isNotEmpty();
  }

  private Optional<com.example.community.service.entity.Post> findPost(UUID boardId, UUID postId) {
    return dslContext
        .select(POST.asterisk())
        .from(POST)
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .fetchOptionalInto(com.example.community.service.entity.Post.class);
  }
}