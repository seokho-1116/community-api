package com.example.community.controller.advice;

import com.example.community.controller.CommunityController;
import com.example.community.controller.response.ErrorResponses;
import com.example.community.service.exception.CommunityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CommunityController.class)
@Slf4j
public class CommunityExceptionAdvice {
  @ExceptionHandler(CommunityNotFoundException.class)
  public ResponseEntity<ErrorResponses> handleCommunityNotFoundException(
      final CommunityNotFoundException exception) {
    log.error("", exception);

    return ResponseEntity.internalServerError()
        .body(ErrorResponses.from(exception.getMessage()));
  }
}
