package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static com.example.api.jooqgen.tables.Post.POST;
import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;

import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
  private final DSLContext dslContext;

  public Page<PostSummaryResponseDto> findPosts(final OffsetDateTime previousDate, final int size) {
    List<PostSummaryResponseDto> dtoList = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            POST.BOARD_ID, BOARD.NAME, POST_CATEGORY.NAME, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.lt(previousDate))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(size)
        .fetchInto(PostSummaryResponseDto.class);

    int count = dslContext.fetchCount(POST);

    return new PageImpl<>(dtoList, Pageable.ofSize(size), count);
  }

  public Page<PostSummaryResponseDto> findPostsByBoardPublicId(final UUID boardPublicId,
      final OffsetDateTime previousDate, final int size) {
    List<PostSummaryResponseDto> dtoList = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            POST.BOARD_ID, BOARD.NAME, POST_CATEGORY.NAME, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.lt(previousDate)
            .and(POST.BOARD_PUBLIC_ID.eq(boardPublicId)))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(size)
        .fetchInto(PostSummaryResponseDto.class);

    int count  = dslContext.fetchCount(POST, POST.BOARD_PUBLIC_ID.eq(boardPublicId));

    return new PageImpl<>(dtoList, Pageable.ofSize(size), count);
  }

  public Optional<PostDetailResponseDto> findPostByBoardPublicIdAndPublicId(final UUID boardPublicId,
      final UUID postPublicId) {
    return dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, MEMBER.NICKNAME,
            MEMBER.PUBLIC_ID.as("memberPublicId"), POST.CREATED_DATE, POST.VIEWS_COUNT,
            POST.UP_VOTES_COUNT, POST.DOWN_VOTES_COUNT, BOARD.NAME.as("boardCategory"),
            POST_CATEGORY.NAME.as("postCategory"), POST.POST_URL)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.BOARD_PUBLIC_ID.eq(boardPublicId)
            .and(POST.PUBLIC_ID.eq(postPublicId)))
        .fetchOptionalInto(PostDetailResponseDto.class);
  }

  public Optional<UUID> findPostIdByPostPublicId(UUID postPublicId) {
    return dslContext
        .select(POST.ID)
        .from(POST)
        .where(POST.PUBLIC_ID.eq(postPublicId))
        .fetchOptionalInto(UUID.class);
  }
}
