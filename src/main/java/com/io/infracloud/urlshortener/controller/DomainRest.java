package com.io.infracloud.urlshortener.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface DomainRest {

  @GetMapping(value = "/api/v1/fetchTopDomainMatrix")
  public ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix();
}
