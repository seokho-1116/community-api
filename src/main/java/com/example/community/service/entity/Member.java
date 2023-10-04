package com.example.community.service.entity;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "modified_date")
  private OffsetDateTime modifiedDate;

  @Column(name = "signup_id")
  private String signupId;

  @Column(name = "signup_password")
  private String signupPassword;

  @Column(name = "email")
  private String email;

  @Column(name = "expiration_date")
  private OffsetDateTime expirationDate;

  @Column(name = "account_locked")
  private Boolean accountLocked;

  @Column(name = "role")
  private String role;
}