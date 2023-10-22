package com.example.community.repository;

import static com.example.api.jooqgen.tables.Comment.COMMENT;
import static com.example.api.jooqgen.tables.Member.MEMBER;

import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.PageCommentRequestDto;
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

  public Page<CommentDetailResponseDto> findComments(PageCommentRequestDto dto) {
    List<CommentDetailResponseDto> dtoList = dslContext
        .select(MEMBER.NICKNAME, COMMENT.CONTENT, COMMENT.CREATED_DATE, COMMENT.UP_VOTES_COUNT,
            COMMENT.DOWN_VOTES_COUNT)
        .from(COMMENT)
        .join(MEMBER).on(COMMENT.MEMBER_ID.eq(MEMBER.ID))
        .where(COMMENT.POST_PUBLIC_ID.eq(dto.getPostPublicId())
            .and(COMMENT.CREATED_DATE.lt(dto.getPreviousDate())))
        .orderBy(COMMENT.CREATED_DATE.desc(), COMMENT.ID.desc())
        .limit(dto.getSize())
        .fetchInto(CommentDetailResponseDto.class);

    int count = dslContext.fetchCount(COMMENT, COMMENT.POST_PUBLIC_ID.eq(dto.getPostPublicId()));

    return new PageImpl<>(dtoList, Pageable.ofSize(dto.getSize()), count);
  }
}
