package com.example.community.controller.advice;

import com.example.community.controller.response.ErrorResponses;
import com.example.community.security.authentication.jwt.exception.InvalidTokenException;
import com.example.community.service.exception.TokenNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TokenExceptionAdvice {
  @ExceptionHandler(TokenNotFoundException.class)
  public ResponseEntity<ErrorResponses> handleTokenNotFoundException(
      TokenNotFoundException exception) {
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from(exception.getMessage()));
  }

  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<ErrorResponses> handleInvalidTokenException(
      InvalidTokenException exception) {
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from(exception.getMessage()));
  }
}
