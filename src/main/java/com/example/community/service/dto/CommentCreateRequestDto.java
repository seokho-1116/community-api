package com.example.community.service.dto;

import com.example.community.service.entity.Comment;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
  private final UUID postId;
  private final UUID memberId;
  private final String content;

  private CommentCreateRequestDto(UUID postId, UUID memberId, String content) {
    this.postId = postId;
    this.memberId = memberId;
    this.content = content;
  }

  public static CommentCreateRequestDto create(UUID postId, UUID memberId, String content) {
    return new CommentCreateRequestDto(postId, memberId, content);
  }

  public Comment toEntity() {
    OffsetDateTime now = OffsetDateTime.now();

    return Comment.builder()
        .id(UUID.randomUUID())
        .publicId(UUID.randomUUID())
        .createdDate(now)
        .modifiedDate(now)
        .content(content)
        .upVotesCount(0)
        .downVotesCount(0)
        .postId(postId)
        .memberId(memberId)
        .build();
  }
}
