package com.example.community.service.entity;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "board")
public class Board {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "name")
  private String name;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "community_id")
  private Community community;
}