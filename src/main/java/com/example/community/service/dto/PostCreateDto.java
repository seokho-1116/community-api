package com.example.community.service.dto;

import com.example.community.service.entity.Post;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PostCreateDto {
  private final String title;
  private final String content;
  private final UUID boardPublicId;
  private final UUID postCategoryPublicId;
  private final UUID memberPublicId;

  public PostCreateDto(String title, String content, UUID boardPublicId, UUID postCategoryPublicId,
      UUID memberPublicId) {
    this.title = title;
    this.content = content;
    this.boardPublicId = boardPublicId;
    this.postCategoryPublicId = postCategoryPublicId;
    this.memberPublicId = memberPublicId;
  }

  //TODO: postUrl setting
  public Post toEntity(UUID boardId, UUID memberId, UUID postCategoryId) {
    OffsetDateTime now = OffsetDateTime.now();

    return Post.builder()
        .id(UUID.randomUUID())
        .publicId(UUID.randomUUID())
        .title(title)
        .content(content)
        .boardId(boardId)
        .boardPublicId(boardPublicId)
        .postCategoryPublicId(postCategoryPublicId)
        .memberId(memberId)
        .memberPublicId(memberPublicId)
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
