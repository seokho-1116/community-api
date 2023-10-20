package com.example.community.service.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Token {
  @Id
  private UUID id;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "member_public_id")
  private UUID memberPublicId;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  protected Token() {
  }

  private Token(UUID id, UUID publicId, String refreshToken, UUID memberPublicId, Role role) {
    this.id = id;
    this.publicId = publicId;
    this.refreshToken = refreshToken;
    this.memberPublicId = memberPublicId;
    this.role = role;
  }

  public static Token create(String refresh, UUID memberPublicId, Role role) {
    return new Token(UUID.randomUUID(), UUID.randomUUID(), refresh, memberPublicId, role);
  }
}
