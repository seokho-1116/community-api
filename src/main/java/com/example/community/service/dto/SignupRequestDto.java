package com.example.community.service.dto;

import com.example.community.service.entity.Member;
import com.example.community.service.entity.Role;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class SignupRequestDto {
  private final String id;
  private final String nickname;
  private final String password;
  private final String email;

  private SignupRequestDto(String id, String nickname, String password, String email) {
    this.id = id;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
  }

  public static SignupRequestDto create(String id, String nickname, String password, String email) {
    return new SignupRequestDto(id, nickname, password, email);
  }

  public Member toEntity(String encodedPassword) {
    OffsetDateTime now = OffsetDateTime.now();

    return Member.builder()
        .id(UUID.randomUUID())
        .publicId(UUID.randomUUID())
        .signupId(id)
        .signupPassword(encodedPassword)
        .nickname(nickname)
        .email(email)
        .createdDate(now)
        .modifiedDate(now)
        .role(Role.USER)
        .accountLocked(false)
        .expirationDate(now.plusYears(5))
        .build();
  }
}
