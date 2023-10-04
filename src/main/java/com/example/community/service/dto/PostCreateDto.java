package com.example.community.service.dto;

import com.example.community.service.entity.Post;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCreateDto {
  private final String title;
  private final String content;
  private final UUID boardId;
  private final UUID postCategoryId;

  public PostCreateDto(String title, String content, UUID boardId, UUID postCategoryId) {
    this.title = title;
    this.content = content;
    this.boardId = boardId;
    this.postCategoryId = postCategoryId;
  }

  public Post toEntity() {
    OffsetDateTime now = OffsetDateTime.now();

    return Post.builder()
        .id(UUID.randomUUID())
        .publicId(UUID.randomUUID())
        .title(title)
        .content(content)
        .boardId(boardId)
        .postCategoryId(postCategoryId)
        .createdDate(now)
        .modifiedDate(now)
        .isFeatured(false)
        .postUrl("")
        .downVotesCount(0)
        .upVotesCount(0)
        .viewsCount(0L)
        .build();
  }
}
