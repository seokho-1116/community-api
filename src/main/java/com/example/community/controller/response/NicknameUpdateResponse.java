package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NicknameUpdateResponse {
  private final String nickname;

  @JsonCreator
  public NicknameUpdateResponse(@JsonProperty("nickname") String nickname) {
    this.nickname = nickname;
  }

  public static NicknameUpdateResponse create(String nickname) {
    return new NicknameUpdateResponse(nickname);
  }
}
