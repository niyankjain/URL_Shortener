package com.io.infracloud.urlshortener.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface DomainRest {

  @GetMapping(value = "/api/v1/fetchTopDomainMatrix")
  public ResponseEntity<List<MatrixResponseDTO>> getTopDomainMatrix();

  @GetMapping(value = "/api/v1/addBlackListedDomain")
  public ResponseEntity<ResponseDTO> addBlackListedDomain(@RequestParam(name = "domain", required = true) String domain);
}
