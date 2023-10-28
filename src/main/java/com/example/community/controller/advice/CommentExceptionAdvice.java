package com.example.community.controller.advice;

import com.example.community.controller.CommentController;
import com.example.community.controller.response.ErrorResponses;
import com.example.community.service.exception.NotResourceOwnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CommentController.class)
@Slf4j
public class CommentExceptionAdvice {
  @ExceptionHandler(NotResourceOwnerException.class)
  public ResponseEntity<ErrorResponses> handleNotResourceOwnerException(
      NotResourceOwnerException exception) {
    log.error("", exception);

    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ErrorResponses.from(exception.getMessage()));
  }

}
