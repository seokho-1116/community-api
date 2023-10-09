package com.example.community.repository;

import static com.example.api.jooqgen.tables.Comment.COMMENT;
import static com.example.api.jooqgen.tables.Member.MEMBER;

import com.example.community.service.dto.CommentDetailDto;
import com.example.community.service.dto.CommentUpdateRequestDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryRepository {
  private final DSLContext dslContext;

  public Page<CommentDetailDto> findComments(UUID postId, OffsetDateTime previousDate,
      Pageable pageable) {
    List<CommentDetailDto> dtos = dslContext
        .select(MEMBER.NICKNAME, COMMENT.CONTENT, COMMENT.CREATED_DATE, COMMENT.UP_VOTES_COUNT,
            COMMENT.DOWN_VOTES_COUNT)
        .from(COMMENT)
        .join(MEMBER).on(COMMENT.MEMBER_ID.eq(MEMBER.ID))
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.CREATED_DATE.lt(previousDate)))
        .orderBy(COMMENT.CREATED_DATE.desc(), COMMENT.ID.desc())
        .limit(pageable.getPageSize())
        .fetchInto(CommentDetailDto.class);

    int count = dslContext
        .fetchCount(COMMENT, COMMENT.POST_ID.eq(postId));

    return new PageImpl<>(dtos, pageable, count);
  }

  @Transactional
  public UUID updateCommentContent(CommentUpdateRequestDto dto) {
    dslContext
        .update(COMMENT)
        .set(COMMENT.CONTENT, dto.getContent())
        .where(COMMENT.POST_ID.eq(dto.getPostId())
            .and(COMMENT.PUBLIC_ID.eq(dto.getCommentId())))
        .execute();

    return dto.getCommentId();
  }

  @Transactional
  public UUID deleteComment(UUID postId, UUID commentId) {
    dslContext
        .delete(COMMENT)
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.PUBLIC_ID.eq(commentId)))
        .execute();

    return commentId;
  }
}
