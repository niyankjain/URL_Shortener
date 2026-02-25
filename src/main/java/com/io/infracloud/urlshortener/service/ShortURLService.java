package com.io.infracloud.urlshortener.service;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;

import com.io.infracloud.urlshortener.dto.LongURLRequestDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;

public interface ShortURLService {
  ResponseEntity<ResponseDTO> constructShortURL(LongURLRequestDTO longURLRequestDTO);

  ResponseEntity<String> getLongURL(String shortCode) ;
}
