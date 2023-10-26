package com.example.community.service.dto;

import com.example.community.service.entity.Comment;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
  private final UUID boardPublicId;
  private final UUID postPublicId;
  private final UUID memberPublicId;
  private final String content;

  private CommentCreateRequestDto(UUID boardPublicId, UUID postPublicId, UUID memberPublicId, String content) {
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
    this.memberPublicId = memberPublicId;
    this.content = content;
  }

  public static CommentCreateRequestDto create(UUID boardPublicId, UUID postId, UUID memberPublicId,
      String content) {
    return new CommentCreateRequestDto(boardPublicId, postId, memberPublicId, content);
  }

  public Comment toEntity(UUID boardId, UUID postId, UUID memberId) {
    OffsetDateTime now = OffsetDateTime.now();

    return Comment.builder()
        .id(UUID.randomUUID())
        .publicId(UUID.randomUUID())
        .createdDate(now)
        .modifiedDate(now)
        .content(content)
        .upVotesCount(0)
        .downVotesCount(0)
        .boardId(boardId)
        .postId(postId)
        .memberId(memberId)
        .boardPublicId(boardPublicId)
        .postPublicId(postPublicId)
        .memberPublicId(memberPublicId)
        .build();
  }
}
