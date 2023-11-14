package com.example.community.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.community.common.TestData.TestDataType;
import com.example.community.repository.CommentJpaRepository;
import com.example.community.repository.CommentQueryRepository;
import com.example.community.service.dto.CommentDeleteRequestDto;
import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.CommentUpdateRequestDto;
import com.example.community.service.dto.PageCommentRequestDto;
import com.example.community.service.entity.Comment;
import com.example.community.service.exception.NotResourceOwnerException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class CommentServiceTest extends ServiceTest {
  private static final UUID BOARD_PUBLIC_ID = UUID.fromString(TEST_DATA.getPostBoardPublicId(
      TestDataType.MY));
  private static final UUID POST_PUBLIC_ID = UUID.fromString(TEST_DATA.getPostPublicId(
      TestDataType.MY));
  private static final UUID MEMBER_PUBLIC_ID = UUID.fromString(TEST_DATA.getPostPublicId(
      TestDataType.MY));
  private static final UUID COMMENT_PUBLIC_ID = UUID.fromString(TEST_DATA.getCommentPublicId(
      TestDataType.MY));
  private static final UUID NULL_UUID = TEST_DATA.getNullUUID();

  @InjectMocks
  private CommentService commentService;

  @Mock
  private CommentQueryRepository commentQueryRepository;

  @Mock
  private CommentJpaRepository commentJpaRepository;

  @DisplayName("댓글_권한이_있는_유저로_조회_테스트")
  @Test
  void findComments() {
    PageCommentRequestDto request = createPageCommentRequestDto(MEMBER_PUBLIC_ID);

    when(commentQueryRepository.findComments(any(PageCommentRequestDto.class)))
        .thenReturn(createTestPage(true));

    Page<CommentDetailResponseDto> page = commentService.findComments(request);

    assertThat(page.getContent()).anyMatch(CommentDetailResponseDto::isOwner);
  }

  private PageCommentRequestDto createPageCommentRequestDto(UUID memberPublicId) {
    return PageCommentRequestDto.create(OffsetDateTime.now(), 20, BOARD_PUBLIC_ID,
        POST_PUBLIC_ID, memberPublicId);
  }

  private Page<CommentDetailResponseDto> createTestPage(boolean isOwner) {
    List<CommentDetailResponseDto> content = IntStream.range(0, 20)
        .mapToObj(d -> createTestComment(UUID.randomUUID(), false))
        .collect(Collectors.toList());

    if (isOwner) {
      content.add(createTestComment(MEMBER_PUBLIC_ID, true));
    }

    return new PageImpl<>(content, Pageable.ofSize(20), 100);
  }

  private CommentDetailResponseDto createTestComment(UUID memberPublicId, boolean isOwner) {
    return new CommentDetailResponseDto(memberPublicId, "nickname", "content",
        OffsetDateTime.now(), 0, 0);
  }


  @DisplayName("댓글_권한이_없는_유저로_조회_테스트")
  @Test
  void findCommentsWithNotResourceOwner() {
    PageCommentRequestDto request = createPageCommentRequestDto(NULL_UUID);

    when(commentQueryRepository.findComments(any(PageCommentRequestDto.class)))
        .thenReturn(createTestPage(false));

    Page<CommentDetailResponseDto> page = commentService.findComments(request);

    assertThat(page.getContent()).noneMatch(CommentDetailResponseDto::isOwner);
  }

  @DisplayName("댓글_권한이_있는_유저로_업데이트_테스트")
  @Test
  void updateCommentWithResourceOwner() {
    CommentUpdateRequestDto request = CommentUpdateRequestDto.create(BOARD_PUBLIC_ID,
        POST_PUBLIC_ID, COMMENT_PUBLIC_ID, MEMBER_PUBLIC_ID, "content");

    when(commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(any(UUID.class),
        any(UUID.class), any(UUID.class))).thenReturn(createComment());

    UUID publicId = commentService.updateComment(request);

    assertThat(publicId).isEqualTo(COMMENT_PUBLIC_ID);
  }

  private Comment createComment() {
    return Comment.builder()
        .memberPublicId(MEMBER_PUBLIC_ID)
        .build();
  }

  @DisplayName("댓글_권한이_없는_유저로_업데이트_테스트")
  @Test
  void updateCommentWithNotResourceOwner() {
    CommentUpdateRequestDto request = CommentUpdateRequestDto.create(BOARD_PUBLIC_ID,
        POST_PUBLIC_ID, COMMENT_PUBLIC_ID, NULL_UUID, "content");

    when(commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(any(UUID.class),
        any(UUID.class), any(UUID.class))).thenReturn(createComment());

    assertThatThrownBy(() -> commentService.updateComment(request))
        .isInstanceOf(NotResourceOwnerException.class);
  }


  @DisplayName("댓글_권한이_있는_유저로_삭제_테스트")
  @Test
  void deleteComment() {
    CommentDeleteRequestDto request = CommentDeleteRequestDto.create(BOARD_PUBLIC_ID,
        POST_PUBLIC_ID, COMMENT_PUBLIC_ID, MEMBER_PUBLIC_ID);

    when(commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(any(UUID.class),
        any(UUID.class), any(UUID.class))).thenReturn(createComment());

    UUID publicId = commentService.deleteComment(request);

    assertThat(publicId).isEqualTo(COMMENT_PUBLIC_ID);
  }

  @DisplayName("댓글_권한이_없는_유저로_삭제_테스트")
  @Test
  void deleteCommentWithNotResourceOwner() {
    CommentDeleteRequestDto request = CommentDeleteRequestDto.create(BOARD_PUBLIC_ID,
        POST_PUBLIC_ID, COMMENT_PUBLIC_ID, NULL_UUID);

    when(commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(any(UUID.class),
        any(UUID.class), any(UUID.class))).thenReturn(createComment());

    assertThatThrownBy(() -> commentService.deleteComment(request))
        .isInstanceOf(NotResourceOwnerException.class);
  }
}