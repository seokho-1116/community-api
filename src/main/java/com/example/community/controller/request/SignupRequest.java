package com.example.community.controller.request;

import com.example.community.service.dto.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SignupRequest {
  private final String id;
  private final String nickname;
  private final String password;
  private final String email;

  @JsonCreator
  public SignupRequest(@JsonProperty("id") String id, @JsonProperty("nickname") String nickname,
      @JsonProperty("password") String password, @JsonProperty("email") String email) {
    this.id = id;
    this.nickname = nickname;
    this.password = password;
    this.email = email;
  }

  public SignupRequestDto toDto() {
    return SignupRequestDto.create(id, nickname, password, email);
  }
}
