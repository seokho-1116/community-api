package com.example.community.service.entity;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Builder
  private Member(UUID id, UUID publicId, String nickname, OffsetDateTime createdDate,
      OffsetDateTime modifiedDate, String signupId, String signupPassword, String email,
      OffsetDateTime expirationDate, Boolean accountLocked, Role role) {
    this.id = id;
    this.publicId = publicId;
    this.nickname = nickname;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
    this.signupId = signupId;
    this.signupPassword = signupPassword;
    this.email = email;
    this.expirationDate = expirationDate;
    this.accountLocked = accountLocked;
    this.role = role;
  }

  public void changeNickname(String nickname) {
    this.nickname = nickname;
  }

  public void changeEmail(String email) {
    this.email = email;
  }

  public void changePassword(String password) {
    this.signupPassword = password;
  }

  public boolean isNotOwner(UUID requestMemberPublicId) {
    return publicId != requestMemberPublicId;
  }
}