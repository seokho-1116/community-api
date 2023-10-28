package com.example.community.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class NicknameUpdateRequest {
  @NotEmpty
  private final String nickname;

  @JsonCreator
  public NicknameUpdateRequest(@JsonProperty("nickname") String nickname) {
    this.nickname = nickname;
  }
}
