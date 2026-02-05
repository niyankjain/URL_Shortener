package com.io.infracloud.urlshortener.controller.impl;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.io.infracloud.urlshortener.controller.ShortURLRest;
import com.io.infracloud.urlshortener.dto.LongURLRecord;
import com.io.infracloud.urlshortener.dto.ResponseDTO;
import com.io.infracloud.urlshortener.service.ShortURLService;
import com.io.infracloud.urlshortener.service.impl.ShortURLServiceImpl;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ShortURLRestImpl implements ShortURLRest {

  private final static Logger LOGGER = LoggerFactory.getLogger(ShortURLRestImpl.class);
  private final ShortURLService shortURLService;

  @Override
  public ResponseEntity<ResponseDTO> constructShortURL(@RequestBody LongURLRecord longURLRecord) {
    LOGGER.info("Inside rest layer going to invoke @constructShortURL for long url: {}",longURLRecord.longURL());
    try{
    return shortURLService.constructShortURL(longURLRecord);
    } catch (Exception e) {
      LOGGER.error("Exception: {}", ExceptionUtils.getStackTrace(e));
      throw e;
    }
  }
}

