package com.example.community.repository;

import static org.assertj.core.api.Assertions.*;

import com.example.community.service.dto.PostCategoryDto;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import com.example.community.service.dto.PostUpdateDto;
import com.example.community.service.entity.Post;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.data.domain.Page;

@JooqTest
class PostQueryRepositoryTest {
  private final PostQueryRepository postQueryRepository;

  public PostQueryRepositoryTest(@Autowired DSLContext dslContext) {
    this.postQueryRepository = new PostQueryRepository(dslContext);
  }

  @Test
  void selectPagePostSummary() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;

    Page<PostSummaryResponseDto> page = postQueryRepository.findPosts(previousDate, size);

    assertThat(page).hasSize(size);
  }

  @Test
  void selectPagePostSummaryByBoardId() {
    OffsetDateTime previousDate = OffsetDateTime.now();
    int size = 10;
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");

    Page<PostSummaryResponseDto> page = postQueryRepository.findPostsByBoardId(boardId,
        previousDate, size);

    assertThat(page).hasSize(size);
  }

  @Test
  void selectBoardPostDetailByPostId() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");
    UUID postId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    Optional<PostDetailResponseDto> dto = postQueryRepository.findBoardPostByPostId(boardId, postId);

    assertThat(dto).isPresent();
  }

  @Test
  void updatePost() {
    PostUpdateDto dto = createTestPostUpdateDto();

    postQueryRepository.updatePost(dto);

    PostDetailResponseDto post = findPost(dto.getBoardId(), dto.getPostId())
        .orElseThrow();

    assertThat(post.getTitle()).isEqualTo(dto.getTitle());
    assertThat(post.getContent()).isEqualTo(dto.getContent());
  }

  @Test
  void deletePost() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");
    UUID postId = UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d");

    postQueryRepository.deletePost(boardId, postId);

    assertThat(findPost(boardId, postId)).isNotPresent();
  }

  @Test
  void selectPostCategories() {
    UUID boardId = UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010");

    List<PostCategoryDto> categoryList = postQueryRepository.findPostCategoryById(boardId);

    assertThat(categoryList).isNotEmpty();
  }

  private PostUpdateDto createTestPostUpdateDto() {
    return new PostUpdateDto("update title", "update content",
        UUID.fromString("cea61637-e18d-4919-bea2-ef0f9ad28010"),
        UUID.fromString("ecd77fcd-6c61-4385-a9fe-fe9dbaa47a6d"));
  }

  private Optional<PostDetailResponseDto> findPost(UUID boardId, UUID postId) {
    return postQueryRepository.findBoardPostByPostId(boardId, postId);
  }
}