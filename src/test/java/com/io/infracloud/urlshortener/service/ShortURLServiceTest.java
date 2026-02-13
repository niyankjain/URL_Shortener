package com.io.infracloud.urlshortener.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.io.infracloud.urlshortener.controller.DomainRest;
import com.io.infracloud.urlshortener.controller.ShortURLRest;
import com.io.infracloud.urlshortener.dto.LongURLRequestDTO;
import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;

@SpringBootTest
public class ShortURLServiceTest {

  @Autowired
  private ShortURLRest shortURLRest;

  @Autowired
  private DomainRest domainRest;

  @Test
  @Transactional
  @Rollback
  void constructShortURLTest() {
    LongURLRequestDTO dto = new LongURLRequestDTO(null);

    Assertions.assertThrows(IllegalArgumentException.class, () -> shortURLRest.constructShortURL(dto));

    String longURL= "https://chatgpt.com/c/698f4a8d-fa34-8322-a64b-a76660c4b8aa";
    dto.setLongURL(longURL);
    ResponseEntity<ResponseDTO> responseDTOResponseEntity = shortURLRest.constructShortURL(dto);
    int value = responseDTOResponseEntity.getStatusCode().value();
    Assertions.assertEquals(200, value);
    String shortCode = responseDTOResponseEntity.getBody().getMessage();
    Assertions.assertEquals(longURL,shortURLRest.getLongURL(shortCode).getBody().getMessage());

    ResponseEntity<ResponseDTO> responseDTOResponseEntity1 = shortURLRest.constructShortURL(dto);
    Assertions.assertEquals(200, responseDTOResponseEntity1.getStatusCode().value());

    String longURL1= "https://chatgpt1.com/c/698f4a8d-fa34-8322-a64b-a76660c4b8aa";
    LongURLRequestDTO dto1 = new LongURLRequestDTO(longURL1);
    ResponseEntity<ResponseDTO> responseDTOResponseEntity2 = shortURLRest.constructShortURL(dto1);
    int value1 = responseDTOResponseEntity2.getStatusCode().value();
    Assertions.assertEquals(200, value1);


  }

  @Test
  void getShortURLTest() {
    Assertions.assertThrows(IllegalArgumentException.class,() -> shortURLRest.getLongURL(null));
    Assertions.assertEquals("Long URL not exists",shortURLRest.getLongURL("xyzer").getBody().getMessage());
  }

  @Test
  void getTopDomainTest() {
    ResponseEntity<List<MatrixResponseDTO>> topDomainMatrix = domainRest.getTopDomainMatrix();
    Assertions.assertEquals(200, topDomainMatrix.getStatusCode().value());
  }
}
