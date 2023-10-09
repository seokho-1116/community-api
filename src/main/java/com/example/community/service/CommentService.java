package com.example.community.service;

import com.example.community.repository.CommentJpaRepository;
import com.example.community.repository.CommentQueryRepository;
import com.example.community.service.dto.CommentCreateRequestDto;
import com.example.community.service.dto.CommentDetailDto;
import com.example.community.service.dto.CommentUpdateRequestDto;
import com.example.community.service.entity.Comment;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
  private final CommentQueryRepository commentQueryRepository;
  private final CommentJpaRepository commentJpaRepository;

  public Page<CommentDetailDto> findComments(String postId, String previousDate,
      Pageable pageable) {
    return commentQueryRepository.findComments(UUID.fromString(postId),
        OffsetDateTime.parse(previousDate), pageable);
  }

  public UUID createComment(CommentCreateRequestDto dto) {
    Comment savedEntity = commentJpaRepository.save(dto.toEntity());

    return savedEntity.getPublicId();
  }

  public UUID updateComment(CommentUpdateRequestDto dto) {
    return commentQueryRepository.updateCommentContent(dto);
  }

  public UUID deleteComment(UUID postId, UUID commentId) {
    return commentQueryRepository.deleteComment(postId, commentId);
  }
}
