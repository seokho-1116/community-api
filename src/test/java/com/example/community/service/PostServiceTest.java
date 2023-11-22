package com.example.community.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.community.common.TestData.TestDataType;
import com.example.community.repository.PostJpaRepository;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostUpdateDto;
import com.example.community.service.entity.Post;
import com.example.community.service.exception.NotResourceOwnerException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class PostServiceTest extends ServiceTest {
  private static final UUID BOARD_PUBLIC_ID = UUID.fromString(TEST_DATA.getPostBoardPublicId(
      TestDataType.MY));
  private static final UUID POST_PUBLIC_ID = UUID.fromString(TEST_DATA.getPostPublicId(
      TestDataType.MY));
  private static final UUID MEMBER_PUBLIC_ID = UUID.fromString(TEST_DATA.getMemberPublicId());
  private static final UUID NULL_UUID = TEST_DATA.getNullUUID();

  @InjectMocks
  private PostService postService;

  @Mock
  private PostQueryRepository postQueryRepository;

  @Mock
  private PostJpaRepository postJpaRepository;

  @DisplayName("게시글_권한이_있는_유저로_게시글_조회_테스트")
  @Test
  void findBoardPostByPostIdWithResourceOwner() {
    when(postQueryRepository.findPostByBoardPublicIdAndPublicId(any(UUID.class), any(UUID.class)))
        .thenReturn(createPostDetailResponseDto());

    PostDetailResponseDto post = postService.findBoardPostByPostId(BOARD_PUBLIC_ID, POST_PUBLIC_ID,
        MEMBER_PUBLIC_ID);

    assertThat(post.isOwner()).isTrue();
  }

  private Optional<PostDetailResponseDto> createPostDetailResponseDto() {
    return Optional.of(new PostDetailResponseDto("", "", "", "",
        UUID.fromString(TEST_DATA.getMemberPublicId()), OffsetDateTime.now(), 0,
        0, 0, "category", "category",
        "url"));
  }

  @DisplayName("게시글_권한이_없는_유저로_게시글_조회_테스트")
  @Test
  void findBoardPostByPostIdWithNotResourceOwnerTest() {
    when(postQueryRepository.findPostByBoardPublicIdAndPublicId(any(UUID.class), any(UUID.class)))
        .thenReturn(createPostDetailResponseDto());

    PostDetailResponseDto post = postService.findBoardPostByPostId(BOARD_PUBLIC_ID, POST_PUBLIC_ID,
        NULL_UUID);

    assertThat(post.isOwner()).isFalse();
  }

  @DisplayName("게시글_권한이_있는_유저로_게시글_업데이트_테스트")
  @Test
  void updatePostWithResourceOwner() {
    PostUpdateDto dto = createPostUpdateDto(BOARD_PUBLIC_ID, POST_PUBLIC_ID, MEMBER_PUBLIC_ID);

    when(postJpaRepository.findPostByBoardPublicIdAndPublicId(any(UUID.class), any(UUID.class)))
        .thenReturn(createPost());

    UUID updatedId = postService.updatePost(dto);

    assertThat(updatedId).isEqualTo(POST_PUBLIC_ID);
  }

  private PostUpdateDto createPostUpdateDto(UUID boardPublicId, UUID postPublicId,
      UUID memberPublicId) {
    return new PostUpdateDto("", "", boardPublicId, postPublicId,
        memberPublicId);
  }

  private Optional<Post> createPost() {
    return Optional.ofNullable(Post.builder()
        .memberPublicId(UUID.fromString(TEST_DATA.getMemberPublicId()))
        .build());
  }

  @DisplayName("게시글_권한이_없는_유저로_게시글_업데이트_테스트")
  @Test
  void updatePostWithNotResourceOwner() {
    PostUpdateDto dto = createPostUpdateDto(BOARD_PUBLIC_ID, POST_PUBLIC_ID, NULL_UUID);

    when(postJpaRepository.findPostByBoardPublicIdAndPublicId(any(UUID.class), any(UUID.class)))
        .thenReturn(createPost());

    assertThatThrownBy(() -> postService.updatePost(dto))
        .isInstanceOf(NotResourceOwnerException.class);
  }

  @DisplayName("게시글_권한이_있는_유저로_게시글_삭제_테스트")
  @Test
  void deletePostWithResourceOwner() {
    when(postJpaRepository.findPostByBoardPublicIdAndPublicId(BOARD_PUBLIC_ID, POST_PUBLIC_ID))
        .thenReturn(createPost());

    UUID deletedId = postService.deletePost(BOARD_PUBLIC_ID, POST_PUBLIC_ID, MEMBER_PUBLIC_ID);

    assertThat(deletedId).isEqualTo(POST_PUBLIC_ID);
  }

  @DisplayName("게시글_권한이_없는_유저로_게시글_삭제_테스트")
  @Test
  void deletePostWithNotResourceOwner() {
    when(postJpaRepository.findPostByBoardPublicIdAndPublicId(BOARD_PUBLIC_ID, POST_PUBLIC_ID))
        .thenReturn(createPost());

    assertThatThrownBy(() -> postService.deletePost(BOARD_PUBLIC_ID, POST_PUBLIC_ID, NULL_UUID))
        .isInstanceOf(NotResourceOwnerException.class);
  }
}