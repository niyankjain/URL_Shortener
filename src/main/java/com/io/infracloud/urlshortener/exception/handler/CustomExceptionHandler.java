package com.io.infracloud.urlshortener.exception.handler;

import jakarta.validation.UnexpectedTypeException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.io.infracloud.urlshortener.dto.ErrorResponseDTO;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(ex.getMessage()));
  }

}
