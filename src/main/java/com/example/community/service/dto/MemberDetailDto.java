package com.example.community.service.dto;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class MemberDetailDto {
  private final String signupId;
  private final String nickname;
  private final String email;
  private final OffsetDateTime createdDate;

  @ConstructorProperties({"signupId", "nickname", "email", "createdDate"})
  public MemberDetailDto(String signupId, String nickname, String email,
      OffsetDateTime createdDate) {
    this.signupId = signupId;
    this.nickname = nickname;
    this.email = email;
    this.createdDate = createdDate;
  }
}
