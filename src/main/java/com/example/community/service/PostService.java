package com.example.community.service;

import com.example.community.repository.BoardQueryRepository;
import com.example.community.repository.MemberQueryRepository;
import com.example.community.repository.PostCategoryQueryRepository;
import com.example.community.repository.PostJpaRepository;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.PostCreateDto;
import com.example.community.service.dto.PostDetailResponseDto;
import com.example.community.service.dto.PostSummaryResponseDto;
import com.example.community.service.dto.PostUpdateDto;
import com.example.community.service.entity.Post;
import com.example.community.service.exception.BoardNotFoundException;
import com.example.community.service.exception.MemberNotFoundException;
import com.example.community.service.exception.NotResourceOwnerException;
import com.example.community.service.exception.PostCategoryNotFoundException;
import com.example.community.service.exception.PostNotFoundException;
import io.jsonwebtoken.lang.Assert;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
  private final PostQueryRepository postQueryRepository;
  private final PostJpaRepository postJpaRepository;
  private final MemberQueryRepository memberQueryRepository;
  private final BoardQueryRepository boardQueryRepository;
  private final PostCategoryQueryRepository postCategoryQueryRepository;

  public Page<PostSummaryResponseDto> findPosts(final OffsetDateTime previousDate, final int size) {
    return postQueryRepository.findPosts(previousDate, size);
  }

  public Page<PostSummaryResponseDto> findPostsByBoardPublicId(final UUID boardPublicId,
      final OffsetDateTime previousDate, final int size) {
    return postQueryRepository.findPostsByBoardPublicId(boardPublicId, previousDate, size);
  }

  public PostDetailResponseDto findBoardPostByPostId(final UUID boardPublicId, final UUID postId,
      final UUID memberPublicId) {
    PostDetailResponseDto dto = postQueryRepository.findPostByBoardPublicIdAndPublicId(
            boardPublicId, postId)
        .orElseThrow(PostNotFoundException::new);

    if (isNotOwner(memberPublicId, dto.getMemberPublicId())) {
      return dto;
    }

    assignOwnership(dto);

    return dto;
  }

  private void assignOwnership(PostDetailResponseDto dto) {
    Assert.isTrue(!dto.isOwner(), "소유권은 한 번만 변경 가능합니다.");

    dto.setIsOwner(true);
  }

  private boolean isNotOwner(final UUID memberPublicId, final UUID requestUserPublicId) {
    return !memberPublicId.equals(requestUserPublicId);
  }

  @Transactional
  public UUID createNewPost(final PostCreateDto dto) {
    UUID memberId = memberQueryRepository.findMemberIdByPublicId(dto.getMemberPublicId())
        .orElseThrow(MemberNotFoundException::new);
    UUID boardId = boardQueryRepository.findBoardIdByPublicId(dto.getBoardPublicId())
        .orElseThrow(BoardNotFoundException::new);
    UUID postCategoryId = postCategoryQueryRepository.findPostCategoryIdByPublicId(
        dto.getPostCategoryPublicId())
        .orElseThrow(PostCategoryNotFoundException::new);

    Post post = dto.toEntity(boardId, memberId, postCategoryId);

    postJpaRepository.save(post);

    return post.getPublicId();
  }

  @Transactional
  public UUID updatePost(final PostUpdateDto dto) {
    Post post = postJpaRepository.findPostByBoardPublicIdAndPublicId(dto.getBoardPublicId(),
            dto.getPostPublicId())
        .orElseThrow(PostNotFoundException::new);

    if (post.isNotOwner(dto.getMemberPublicId())) {
      throw NotResourceOwnerException.ofPost();
    }

    post.changeContent(dto.getContent());
    post.changeTitle(dto.getTitle());

    return dto.getPostPublicId();
  }

  @Transactional
  public UUID deletePost(final UUID boardPublicId, final UUID postPublicId,
      final UUID memberPublicId) {
    Post post = postJpaRepository.findPostByBoardPublicIdAndPublicId(boardPublicId, postPublicId)
        .orElseThrow(PostCategoryNotFoundException::new);

    if (post.isNotOwner(memberPublicId)) {
      throw NotResourceOwnerException.ofPost();
    }

    postJpaRepository.remove(post);

    return postPublicId;
  }
}