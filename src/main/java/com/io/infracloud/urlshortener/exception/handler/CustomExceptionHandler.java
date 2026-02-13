package com.io.infracloud.urlshortener.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.io.infracloud.urlshortener.dto.ErrorResponseDTO;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(ex.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException ex) {
    return ResponseEntity.internalServerError().body(new ErrorResponseDTO(ex.getMessage()));
  }

}
