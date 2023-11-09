package com.example.community.service;

import com.example.community.repository.BoardQueryRepository;
import com.example.community.repository.CommentJpaRepository;
import com.example.community.repository.CommentQueryRepository;
import com.example.community.repository.MemberQueryRepository;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.CommentCreateRequestDto;
import com.example.community.service.dto.CommentDeleteRequestDto;
import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.CommentUpdateRequestDto;
import com.example.community.service.dto.PageCommentRequestDto;
import com.example.community.service.entity.Comment;
import com.example.community.service.exception.BoardNotFoundException;
import com.example.community.service.exception.MemberNotFoundException;
import com.example.community.service.exception.NotResourceOwnerException;
import com.example.community.service.exception.PostNotFoundException;
import io.jsonwebtoken.lang.Assert;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
  private final CommentQueryRepository commentQueryRepository;
  private final CommentJpaRepository commentJpaRepository;
  private final BoardQueryRepository boardQueryRepository;
  private final PostQueryRepository postQueryRepository;
  private final MemberQueryRepository memberQueryRepository;

  public Page<CommentDetailResponseDto> findComments(PageCommentRequestDto dto) {
    Page<CommentDetailResponseDto> page = commentQueryRepository.findComments(dto);

    if (isNullValue(dto.getMemberPublicId())) {
      return page;
    }

    for (CommentDetailResponseDto comment : page.getContent()) {
      if (isOwnerOfComment(dto, comment)) {
        assignOwnership(comment);
      }
    }

    return page;
  }

  private boolean isNullValue(UUID memberPublicId) {
    return memberPublicId.getMostSignificantBits() == 0
        && memberPublicId.getLeastSignificantBits() == 0;
  }

  private boolean isOwnerOfComment(PageCommentRequestDto dto, CommentDetailResponseDto comment) {
    return dto.getMemberPublicId().equals(comment.getMemberPublicId());
  }

  private void assignOwnership(CommentDetailResponseDto dto) {
    Assert.isTrue(!dto.isOwner(), "소유권은 한 번만 변경 가능합니다.");

    dto.setIsOwner(true);
  }

  @Transactional
  public UUID createComment(CommentCreateRequestDto dto) {
    UUID boardId = boardQueryRepository.findBoardIdByPublicId(dto.getBoardPublicId())
        .orElseThrow(BoardNotFoundException::new);
    UUID postId = postQueryRepository.findPostIdByPostPublicId(dto.getPostPublicId())
        .orElseThrow(PostNotFoundException::new);
    UUID memberId = memberQueryRepository.findMemberIdByPublicId(dto.getMemberPublicId())
        .orElseThrow(MemberNotFoundException::new);

    Comment savedEntity = commentJpaRepository.save(dto.toEntity(boardId, postId, memberId));

    return savedEntity.getPublicId();
  }

  @Transactional
  public UUID updateComment(CommentUpdateRequestDto dto) {
    Comment comment = commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(
        dto.getBoardPublicId(), dto.getPostPublicId(), dto.getCommentPublicId());

    if (comment.isNotOwner(dto.getMemberPublicId())) {
      throw NotResourceOwnerException.ofComment();
    }

    comment.changeContent(dto.getContent());

    return dto.getCommentPublicId();
  }

  @Transactional
  public UUID deleteComment(CommentDeleteRequestDto dto) {
    Comment comment = commentJpaRepository.findByBoardPublicIdAndPostPublicIdAndPublicId(
        dto.getBoardPublicId(), dto.getPostPublicId(), dto.getCommentPublicId());

    if (comment.isNotOwner(dto.getMemberPublicId())) {
      throw NotResourceOwnerException.ofComment();
    }

    commentJpaRepository.remove(comment);

    return dto.getCommentPublicId();
  }
}
