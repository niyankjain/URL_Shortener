package com.io.infracloud.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatrixResponseDTO {
  private String domain;
  private Long urlCount;
}
