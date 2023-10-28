package com.example.community.controller.advice;

import com.example.community.controller.MemberController;
import com.example.community.controller.response.ErrorResponses;
import com.example.community.service.exception.MemberNotFoundException;
import com.example.community.service.exception.NotResourceOwnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MemberController.class)
@Slf4j
public class MemberExceptionAdvice {
  @ExceptionHandler(MemberNotFoundException.class)
  public ResponseEntity<ErrorResponses> handleMemberNotFoundException(
      MemberNotFoundException exception) {
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from(exception.getMessage()));
  }

  @ExceptionHandler(NotResourceOwnerException.class)
  public ResponseEntity<ErrorResponses> handleNotResourceOwnerException(
      NotResourceOwnerException exception) {
    log.error("", exception);

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponses.from(exception.getMessage()));
  }
}
