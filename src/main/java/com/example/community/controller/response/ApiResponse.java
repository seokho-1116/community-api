package com.example.community.controller.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
  private final String message;

  private final T data;

  @JsonCreator
  private ApiResponse(@JsonProperty("message") String message, @JsonProperty("data") T data) {
    this.message = message;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data);
  }
}