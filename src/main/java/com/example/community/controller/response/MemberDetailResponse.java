package com.example.community.controller.response;

import com.example.community.service.dto.MemberDetailDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class MemberDetailResponse {
  private final String signupId;
  private final String nickname;
  private final String email;
  private final OffsetDateTime createdDate;

  @JsonCreator
  private MemberDetailResponse(@JsonProperty("signupId") String signupId,
      @JsonProperty("nickname") String nickname, @JsonProperty("email") String email,
      @JsonProperty("createdDate") OffsetDateTime createdDate) {
    this.signupId = signupId;
    this.nickname = nickname;
    this.email = email;
    this.createdDate = createdDate;
  }

  public static MemberDetailResponse create(MemberDetailDto dto) {
    return new MemberDetailResponse(dto.getSignupId(), dto.getNickname(), dto.getEmail(),
        dto.getCreatedDate());
  }
}
