package com.example.community.repository;

import static com.example.api.jooqgen.tables.Comment.COMMENT;
import static com.example.api.jooqgen.tables.Member.MEMBER;
import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.CommentDetailDto;
import com.example.community.service.entity.Comment;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@JooqTest
@TestConstructor(autowireMode = AutowireMode.ANNOTATED)
class CommentQueryRepositoryTest {
  @Autowired
  private DSLContext dslContext;

  @Test
  void findComments() {
    UUID postId = UUID.fromString("4c063a22-5716-4012-a770-57299395ecdc");
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    List<CommentDetailDto> dtos = dslContext
        .select(MEMBER.NICKNAME, COMMENT.CONTENT, COMMENT.CREATED_DATE, COMMENT.UP_VOTES_COUNT,
            COMMENT.DOWN_VOTES_COUNT)
        .from(COMMENT)
        .join(MEMBER).on(COMMENT.MEMBER_ID.eq(MEMBER.ID))
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.CREATED_DATE.lt(previousDate)))
        .orderBy(COMMENT.CREATED_DATE.desc(), COMMENT.ID.desc())
        .limit(size)
        .fetchInto(CommentDetailDto.class);

    assertThat(dtos).hasSize(10);
  }

  @Test
  void updateCommentContent() {
    UUID postId = UUID.fromString("4c063a22-5716-4012-a770-57299395ecdc");
    UUID commentId = UUID.fromString("ba585502-4506-45b7-b38d-78d3d4314425");
    String content = "newContent";

    dslContext
        .update(COMMENT)
        .set(COMMENT.CONTENT, content)
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.PUBLIC_ID.eq(commentId)))
        .execute();

    Comment comment = findComment(postId, commentId)
        .orElseThrow();

    assertThat(comment.getContent()).isEqualTo(content);
  }

  private Optional<Comment> findComment(UUID postId, UUID commentId) {
    return dslContext.select(COMMENT.asterisk())
        .from(COMMENT)
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.PUBLIC_ID.eq(commentId)))
        .fetchOptionalInto(Comment.class);
  }

  @Test
  void deleteComment() {
    UUID postId = UUID.fromString("4c063a22-5716-4012-a770-57299395ecdc");
    UUID commentId = UUID.fromString("ba585502-4506-45b7-b38d-78d3d4314425");

    dslContext
        .delete(COMMENT)
        .where(COMMENT.POST_ID.eq(postId)
            .and(COMMENT.PUBLIC_ID.eq(commentId)))
        .execute();

    assertThat(findComment(postId, commentId)).isNotPresent();
  }
}