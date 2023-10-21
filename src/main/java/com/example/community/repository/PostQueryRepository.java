package com.example.community.repository;

import static com.example.api.jooqgen.tables.Board.BOARD;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static com.example.api.jooqgen.tables.Post.POST;
import static com.example.api.jooqgen.tables.PostCategory.POST_CATEGORY;

import com.example.community.service.dto.PostCategoryDto;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import com.example.community.service.dto.PostUpdateDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
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

  public Page<PostSummaryResponseDto> findPostsByBoardId(final UUID boardId,
      final OffsetDateTime previousDate, final int size) {
    List<PostSummaryResponseDto> dtoList = dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, POST.VIEWS_COUNT, MEMBER.NICKNAME,
            POST.BOARD_ID, BOARD.NAME, POST_CATEGORY.NAME, POST.CREATED_DATE)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.CREATED_DATE.lt(previousDate)
            .and(POST.BOARD_ID.eq(boardId)))
        .orderBy(POST.CREATED_DATE.desc(), POST.ID.desc())
        .limit(size)
        .fetchInto(PostSummaryResponseDto.class);

    int count  = dslContext.fetchCount(POST, POST.BOARD_ID.eq(boardId));

    return new PageImpl<>(dtoList, Pageable.ofSize(size), count);
  }

  public Optional<PostDetailResponseDto> findBoardPostByPostId(final UUID boardId, final UUID postId) {
    return dslContext
        .select(POST.PUBLIC_ID, POST.TITLE, POST.CONTENT, MEMBER.PUBLIC_ID, MEMBER.NICKNAME,
            POST.CREATED_DATE, POST.VIEWS_COUNT, POST.UP_VOTES_COUNT, POST.DOWN_VOTES_COUNT,
            BOARD.NAME, POST_CATEGORY.NAME, POST.POST_URL)
        .from(POST)
        .join(MEMBER).on(POST.MEMBER_ID.eq(MEMBER.ID))
        .join(POST_CATEGORY).on(POST.POST_CATEGORY_ID.eq(POST_CATEGORY.ID))
        .join(BOARD).on(POST.BOARD_ID.eq(BOARD.ID))
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .fetchOptionalInto(PostDetailResponseDto.class);
  }

  public String updatePost(final PostUpdateDto dto) {
    dslContext
        .update(POST)
        .set(POST.TITLE, dto.getTitle())
        .set(POST.CONTENT, dto.getContent())
        .where(POST.BOARD_ID.eq(dto.getBoardId())
            .and(POST.PUBLIC_ID.eq(dto.getPostId())))
        .execute();

    return dto.getPostId().toString();
  }

  public String deletePost(final UUID boardId, final UUID postId) {
    dslContext
        .delete(POST)
        .where(POST.BOARD_ID.eq(boardId)
            .and(POST.PUBLIC_ID.eq(postId)))
        .execute();

    return postId.toString();
  }

  public List<PostCategoryDto> findPostCategoryById(final UUID boardId) {
    return dslContext
        .select(POST_CATEGORY.NAME, POST_CATEGORY.DESCRIPTION)
        .from(POST_CATEGORY)
        .where(POST_CATEGORY.BOARD_ID.eq(boardId))
        .fetchInto(PostCategoryDto.class);
  }
}
