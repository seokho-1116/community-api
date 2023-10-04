package com.example.community.service.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post_category")
public class PostCategory {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;
}