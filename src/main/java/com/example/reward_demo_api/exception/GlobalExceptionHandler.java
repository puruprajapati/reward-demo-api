package com.example.reward_demo_api.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
    ApiError error = new ApiError(
      HttpStatus.BAD_REQUEST.value(),
      "Invalid Request",
      ex.getMessage(),
      request.getRequestURI()
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {
    ApiError error = new ApiError(
      HttpStatus.INTERNAL_SERVER_ERROR.value(),
      "Internal Server Error",
      ex.getMessage(),
      request.getRequestURI()
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
