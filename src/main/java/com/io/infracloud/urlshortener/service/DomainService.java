package com.io.infracloud.urlshortener.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;

public interface DomainService {
  ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix();

  ResponseEntity<ResponseDTO> addBlackListedDomain(String domain);
}
