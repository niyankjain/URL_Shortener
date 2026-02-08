package com.io.infracloud.urlshortener.controller.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.io.infracloud.urlshortener.controller.DomainRest;
import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.service.DomainService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DomainRestImpl implements DomainRest {

  private final DomainService domainService;

  @Override
  public ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix() {
    return domainService.getTopDomainMatrix();
  }
}
