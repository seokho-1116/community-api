package com.example.community.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.community.common.TestData.TestDataType;
import com.example.community.controller.request.PostUpdateRequest;
import com.example.community.repository.PostJpaRepository;
import com.example.community.service.entity.Post;
import java.util.UUID;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostJpaRepositoryTest extends JpaRepositoryTest {
  private final EntityManager entityManager;
  private final PostJpaRepository postJpaRepository;

  public PostJpaRepositoryTest(@Autowired EntityManager entityManager) {
    this.entityManager = entityManager;
    this.postJpaRepository = new PostJpaRepository(entityManager);
  }

  @Test
  void findPostByBoardPublicIdAndPublicId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getPostPublicId(TestDataType.COMMON));

    Post post = postJpaRepository.findPostByBoardPublicIdAndPublicId(boardPublicId, postPublicId);

    assertThat(post).isNotNull();
  }

  @Test
  void updatePost() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getPostPublicId(TestDataType.COMMON));
    Post post = postJpaRepository.findPostByBoardPublicIdAndPublicId(boardPublicId, postPublicId);
    PostUpdateRequest request = new PostUpdateRequest("new title", "new content");

    post.changeTitle(request.getTitle());
    post.changeContent(request.getContent());
    entityManager.flush();
    entityManager.clear();

    SoftAssertions.assertSoftly(assertUpdatedPostProperties(post));
  }

  private Consumer<SoftAssertions> assertUpdatedPostProperties(Post post) {
    return softly -> {
      softly.assertThat(post.getTitle()).isNotEmpty();
      softly.assertThat(post.getContent()).isNotEmpty();
    };
  }
}