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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PostJpaRepositoryTest extends JpaRepositoryTest {
  private final EntityManager entityManager;
  private final PostJpaRepository postJpaRepository;

  public PostJpaRepositoryTest(@Autowired EntityManager entityManager) {
    this.entityManager = entityManager;
    this.postJpaRepository = new PostJpaRepository(entityManager);
  }

  @DisplayName("게시판_공개_키와_게시글_공개_키로_게시글_엔티티_조회_쿼리_테스트")
  @Test
  void findPostByBoardPublicIdAndPublicId() {
    UUID boardPublicId = UUID.fromString(TEST_DATA.getPostBoardPublicId(TestDataType.COMMON));
    UUID postPublicId = UUID.fromString(TEST_DATA.getPostPublicId(TestDataType.COMMON));

    Post post = postJpaRepository.findPostByBoardPublicIdAndPublicId(boardPublicId, postPublicId);

    assertThat(post).isNotNull();
  }

  @DisplayName("게시글_정보_업데이트_쿼리_테스트")
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