package com.example.community.controller.advice;

import com.example.community.controller.BoardController;
import com.example.community.controller.response.ErrorResponses;
import com.example.community.service.exception.BoardNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BoardController.class)
@Slf4j
public class BoardExceptionAdvice {
  @ExceptionHandler(BoardNotFoundException.class)
  public ResponseEntity<ErrorResponses> handleBoardNotFoundException(
      BoardNotFoundException exception) {
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from(exception.getMessage()));
  }
}
