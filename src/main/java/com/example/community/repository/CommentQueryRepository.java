package com.example.community.repository;

import static com.example.api.jooqgen.tables.Comment.COMMENT;
import static com.example.api.jooqgen.tables.Member.MEMBER;

import com.example.community.service.dto.CommentDetailResponseDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {
  private final DSLContext dslContext;

  public Page<CommentDetailResponseDto> findComments(UUID postPublicId, OffsetDateTime previousDate,
      int size) {
    List<CommentDetailResponseDto> dtoList = dslContext
        .select(MEMBER.NICKNAME, COMMENT.CONTENT, COMMENT.CREATED_DATE, COMMENT.UP_VOTES_COUNT,
            COMMENT.DOWN_VOTES_COUNT)
        .from(COMMENT)
        .join(MEMBER).on(COMMENT.MEMBER_ID.eq(MEMBER.ID))
        .where(COMMENT.POST_PUBLIC_ID.eq(postPublicId)
            .and(COMMENT.CREATED_DATE.lt(previousDate)))
        .orderBy(COMMENT.CREATED_DATE.desc(), COMMENT.ID.desc())
        .limit(size)
        .fetchInto(CommentDetailResponseDto.class);

    int count = dslContext
        .fetchCount(COMMENT, COMMENT.POST_ID.eq(postPublicId));

    return new PageImpl<>(dtoList, Pageable.ofSize(size), count);
  }
}
