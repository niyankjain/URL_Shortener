package com.io.infracloud.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponseDTO {
  private String errorMessage;

}
