package com.io.infracloud.urlshortener.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;

public interface DomainService {
  ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix();
}
