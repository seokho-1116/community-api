package com.example.community.service.entity;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "post")
@Getter
public class Post {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "modified_date")
  private OffsetDateTime modifiedDate;

  @Column(name = "views_count")
  private Long viewsCount;

  @Column(name = "up_votes_count")
  private Integer upVotesCount;

  @Column(name = "down_votes_count")
  private Integer downVotesCount;

  @Column(name = "is_featured")
  private Boolean isFeatured;

  @Column(name = "post_url")
  private String postUrl;

  @Column(name = "board_id")
  private UUID boardId;

  @Column(name = "post_category_id")
  private UUID postCategoryId;

  @Column(name = "member_id")
  private UUID memberId;

  @Column(name = "board_public_id")
  private UUID boardPublicId;

  @Column(name = "post_category_public_id")
  private UUID postCategoryPublicId;

  @Column(name = "member_public_id")
  private UUID memberPublicId;

  protected Post() {
  }

  @Builder
  private Post(UUID id, UUID publicId, String title, String content, OffsetDateTime createdDate,
      OffsetDateTime modifiedDate, Long viewsCount, Integer upVotesCount, Integer downVotesCount,
      Boolean isFeatured, String postUrl, UUID boardId, UUID postCategoryId, UUID memberId,
      UUID boardPublicId, UUID postCategoryPublicId, UUID memberPublicId) {
    this.id = id;
    this.publicId = publicId;
    this.title = title;
    this.content = content;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
    this.viewsCount = viewsCount;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
    this.isFeatured = isFeatured;
    this.postUrl = postUrl;
    this.boardId = boardId;
    this.postCategoryId = postCategoryId;
    this.memberId = memberId;
    this.boardPublicId = boardPublicId;
    this.postCategoryPublicId = postCategoryPublicId;
    this.memberPublicId = memberPublicId;
  }

  public void changeContent(String content) {
    this.content = content;
  }

  public void changeTitle(String title) {
    this.title = title;
  }

  public boolean isNotOwner(UUID requestMemberPublicId) {
    return !memberPublicId.equals(requestMemberPublicId);
  }
}