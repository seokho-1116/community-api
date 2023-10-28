package com.example.community.controller.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.validation.FieldError;

public class ErrorResponses {
  private final List<ErrorResponse> errorResponses = new ArrayList<>();
  
  private ErrorResponses(String message) {
    this.errorResponses.add(ErrorResponse.from(message));
  }

  private ErrorResponses(List<ErrorResponse> errorResponses) {
    this.errorResponses.addAll(errorResponses);
  }

  public static ErrorResponses from(String message) {
    return new ErrorResponses(message);
  }

  public static ErrorResponses fromFieldErrors(List<FieldError> fieldErrors) {
    List<ErrorResponse> errorResponses = fieldErrors
        .stream()
        .map(error -> ErrorResponse.ofFieldError(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());

    return new ErrorResponses(errorResponses);
  }

  public List<ErrorResponse> getErrorResponses() {
    return Collections.unmodifiableList(errorResponses);
  }

  @Getter
  public static class ErrorResponse {
    private final String message;

    private ErrorResponse(String message) {
      this.message = message;
    }

    private static ErrorResponse from(String message) {
      return new ErrorResponse(message);
    }

    private static ErrorResponse ofFieldError(String field, String message) {
      return new ErrorResponse(field + " " + message);
    }
  }
}
