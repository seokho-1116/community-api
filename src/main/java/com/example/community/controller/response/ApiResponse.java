package com.example.community.controller.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
  private final String msg;

  private final T data;

  private ApiResponse(String msg, T data) {
    this.msg = msg;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("success", data);
  }
}