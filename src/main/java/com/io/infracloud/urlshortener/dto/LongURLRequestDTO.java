package com.io.infracloud.urlshortener.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LongURLRequestDTO {

//  @NotBlank(message = "Please provide your URL")
  private String longURL;
}
