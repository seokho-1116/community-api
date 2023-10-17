package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class NicknameUpdateResponse {
  private final String nickname;

  public NicknameUpdateResponse(String nickname) {
    this.nickname = nickname;
  }

  public static NicknameUpdateResponse create(String nickname) {
    return new NicknameUpdateResponse(nickname);
  }
}
