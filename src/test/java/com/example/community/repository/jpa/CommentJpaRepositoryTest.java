package com.example.community.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.common.TestData.TestDataType;
import com.example.community.repository.CommentJpaRepository;
import com.example.community.service.entity.Comment;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CommentJpaRepositoryTest extends JpaRepositoryTest {
  private final EntityManager entityManager;
  private final CommentJpaRepository commentJpaRepository;

  public CommentJpaRepositoryTest(@Autowired EntityManager entityManager) {
    this.entityManager = entityManager;
    this.commentJpaRepository = new CommentJpaRepository(entityManager);
  }

  @Test
  void findByBoardPublicIdAndPostPublicIdAndPublicId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getCommentBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getCommentPostPublicId(TestDataType.COMMON));
    UUID commentPublicId = UUID.fromString(TEST_DATA.getCommentPublicId(TestDataType.COMMON));

    Comment comment = commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(
        boardPublicId, postPublicId, commentPublicId);

    assertThat(comment).isNotNull();
  }

  @Test
  void updateComment() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getCommentBoardPublicId(TestDataType.FOR_UPDATE));
    UUID postPublicId = UUID.fromString(TEST_DATA.getCommentPostPublicId(TestDataType.FOR_UPDATE));
    UUID commentPublicId = UUID.fromString(TEST_DATA.getCommentPublicId(TestDataType.FOR_UPDATE));
    Comment comment = commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(
        boardPublicId, postPublicId, commentPublicId);
    String newContent = "new content";

    comment.changeContent(newContent);
    entityManager.flush();
    entityManager.clear();

    Comment updatedComment = commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(
        boardPublicId, postPublicId, commentPublicId);
    assertThat(updatedComment.getContent()).isEqualTo(newContent);
  }
}