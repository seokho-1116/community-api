package com.example.community.controller.advice;

import com.example.community.controller.response.ErrorResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jooq.exception.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<ErrorResponses> handleDataAccessException(
      final DataAccessException exception) {
    log.error("", exception);

    return ResponseEntity.internalServerError()
        .body(ErrorResponses.from("There was a database error, please try again later."));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponses> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException exception){
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from(
            "The type of the request path value is incorrect, please correct the type. (UUID)"));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponses> handleConstraintViolationException(
      MethodArgumentNotValidException exception) {
    log.error("", exception);

    List<FieldError> fieldErrors = exception.getBindingResult()
        .getFieldErrors();

    return ResponseEntity.badRequest()
        .body(ErrorResponses.fromFieldErrors(fieldErrors));
  }

  @ExceptionHandler(MethodNotAllowed.class)
  public ResponseEntity<ErrorResponses> handleMethodNotAllowedException(MethodNotAllowed exception) {
    log.error("", exception);

    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ErrorResponses.from("This is an unsupported HttpMethod."));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponses> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    log.error("", exception);

    return ResponseEntity.badRequest()
        .body(ErrorResponses.from("Your request is invalid. Please read the RestDocs again"));
  }
}
