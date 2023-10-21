package com.example.community.service;

import com.example.community.repository.CommentJpaRepository;
import com.example.community.repository.CommentQueryRepository;
import com.example.community.repository.MemberQueryRepository;
import com.example.community.repository.PostQueryRepository;
import com.example.community.service.dto.CommentCreateRequestDto;
import com.example.community.service.dto.CommentDetailResponseDto;
import com.example.community.service.dto.CommentUpdateRequestDto;
import com.example.community.service.entity.Comment;
import com.example.community.service.exception.MemberNotFoundException;
import com.example.community.service.exception.PostNotExistException;
import java.time.OffsetDateTime;
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
  private final PostQueryRepository postQueryRepository;
  private final MemberQueryRepository memberQueryRepository;

  public Page<CommentDetailResponseDto> findComments(UUID postPublicId, OffsetDateTime previousDate,
      int size) {
    return commentQueryRepository.findComments(postPublicId, previousDate, size);
  }

  @Transactional
  public UUID createComment(CommentCreateRequestDto dto) {
    UUID postId = postQueryRepository.findPostIdByPostPublicId(dto.getPostPublicId())
        .orElseThrow(PostNotExistException::new);
    UUID memberId = memberQueryRepository.findMemberIdByPostPublicId(dto.getMemberPublicId())
        .orElseThrow(MemberNotFoundException::new);

    Comment savedEntity = commentJpaRepository.save(dto.toEntity(postId, memberId));

    return savedEntity.getPublicId();
  }

  @Transactional
  public UUID updateComment(CommentUpdateRequestDto dto) {
    Comment comment = commentJpaRepository.findByPostPublicIdAndPublicId(dto.getPostPublicId(),
        dto.getCommentPublicId());

    comment.changeContent(dto.getContent());

    return dto.getCommentPublicId();
  }

  @Transactional
  public UUID deleteComment(UUID postPublicId, UUID commentPublicId) {
    Comment comment = commentJpaRepository.findByPostPublicIdAndPublicId(postPublicId,
        commentPublicId);

    commentJpaRepository.remove(comment);

    return commentPublicId;
  }
}
