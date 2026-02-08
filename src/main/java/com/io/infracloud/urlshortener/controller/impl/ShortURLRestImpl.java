package com.io.infracloud.urlshortener.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.io.infracloud.urlshortener.controller.ShortURLRest;
import com.io.infracloud.urlshortener.dto.LongURLRequestDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;
import com.io.infracloud.urlshortener.service.ShortURLService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ShortURLRestImpl implements ShortURLRest {

  private final static Logger LOGGER = LoggerFactory.getLogger(ShortURLRestImpl.class);
  private final ShortURLService shortURLService;

  @Override
  public ResponseEntity<ResponseDTO> constructShortURL(LongURLRequestDTO longURLRequestDTO) {
    LOGGER.info("Inside rest layer going to invoke @constructShortURL");
    return shortURLService.constructShortURL(longURLRequestDTO);
  }
}

