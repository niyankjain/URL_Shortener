package com.io.infracloud.urlshortener.service;

import org.springframework.http.ResponseEntity;

import com.io.infracloud.urlshortener.dto.LongURLRecord;
import com.io.infracloud.urlshortener.dto.ResponseDTO;

public interface ShortURLService {
  ResponseEntity<ResponseDTO> constructShortURL(LongURLRecord longURLRecord);
}
