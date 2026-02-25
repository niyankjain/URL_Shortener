package com.io.infracloud.urlshortener.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;
import com.io.infracloud.urlshortener.repository.ShortURLRepository;
import com.io.infracloud.urlshortener.service.DomainService;
import com.io.infracloud.urlshortener.utils.UrlUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DomainServiceImpl implements DomainService {

  private final ShortURLRepository shortURLRepository;

  @Override
  public ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix() {
    return ResponseEntity.ok().body(shortURLRepository.findTopDomains(PageRequest.of(0, 3)));
  }

  @Override
  public ResponseEntity<ResponseDTO> addBlackListedDomain(String domain) {
    if(UrlUtils.BLACKLISTED_DOMAINS == null) {
      UrlUtils.BLACKLISTED_DOMAINS = new ArrayList<>();
    }
    UrlUtils.BLACKLISTED_DOMAINS.add(domain);
    return ResponseEntity.ok().body(new ResponseDTO("Domain added as blacklisted"));
  }
}
