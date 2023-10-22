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
@Table(name = "comment")
@Getter
public class Comment {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "content")
  private String content;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "modified_date")
  private OffsetDateTime modifiedDate;

  @Column(name = "up_votes_count")
  private Integer upVotesCount;

  @Column(name = "down_votes_count")
  private Integer downVotesCount;

  @Column(name = "board_id")
  private UUID boardId;

  @Column(name = "post_id")
  private UUID postId;

  @Column(name = "member_id")
  private UUID memberId;

  @Column(name = "board_public_id")
  private UUID boardPublicId;

  @Column(name = "post_public_id")
  private UUID postPublicId;

  @Column(name = "member_public_id")
  private UUID memberPublicId;

  protected Comment() {
  }

  @Builder
  private Comment(UUID id, UUID publicId, String content, OffsetDateTime createdDate,
      OffsetDateTime modifiedDate, Integer upVotesCount, Integer downVotesCount, UUID boardId, UUID postId,
      UUID memberId, UUID boardPublicId, UUID postPublicId, UUID memberPublicId) {
    this.id = id;
    this.publicId = publicId;
    this.content = content;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
    this.upVotesCount = upVotesCount;
    this.downVotesCount = downVotesCount;
    this.boardId = boardId;
    this.postId = postId;
    this.memberId = memberId;
    this.boardPublicId = boardPublicId;
    this.postPublicId = postPublicId;
    this.memberPublicId = memberPublicId;
  }

  public void changeContent(String content) {
    this.content = content;
  }
}