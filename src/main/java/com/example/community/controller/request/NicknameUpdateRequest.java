package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NicknameUpdateRequest {
  private final String nickname;

  @JsonCreator
  public NicknameUpdateRequest(@JsonProperty("nickname") String nickname) {
    this.nickname = nickname;
  }
}
