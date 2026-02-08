package com.io.infracloud.urlshortener.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.repository.DomainRepository;
import com.io.infracloud.urlshortener.repository.ShortURLRepository;
import com.io.infracloud.urlshortener.service.DomainService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DomainServiceImpl implements DomainService {

  private final ShortURLRepository shortURLRepository;

  @Override
  public ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix() {
    return ResponseEntity.ok().body(shortURLRepository.findTopDomains(PageRequest.of(0, 3)));
  }
}
