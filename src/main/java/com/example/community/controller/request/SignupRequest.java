package com.example.community.controller.request;

import com.example.community.service.dto.SignupRequestDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class SignupRequest {
  @NotEmpty
  private final String id;

  @NotEmpty
  private final String nickname;

  @NotEmpty
  private final String password;

  @Email
  @NotEmpty
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
