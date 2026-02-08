package com.io.infracloud.urlshortener.dto;

import jakarta.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
  private String message;
}
