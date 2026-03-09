package com.io.infracloud.urlshortener.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hashids.Hashids;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.io.infracloud.urlshortener.dto.LongURLRequestDTO;
import com.io.infracloud.urlshortener.dto.ResponseDTO;
import com.io.infracloud.urlshortener.entity.Domain;
import com.io.infracloud.urlshortener.entity.ShortURL;
import com.io.infracloud.urlshortener.repository.DomainRepository;
import com.io.infracloud.urlshortener.repository.ShortURLRepository;
import com.io.infracloud.urlshortener.service.ShortURLService;
import com.io.infracloud.urlshortener.utils.HashUtils;
import com.io.infracloud.urlshortener.utils.UrlUtils;

import lombok.AllArgsConstructor;
import net.bull.javamelody.internal.common.LOG;

@AllArgsConstructor
@Service
public class ShortURLServiceImpl implements ShortURLService {

  private final static Logger LOGGER = LoggerFactory.getLogger(ShortURLServiceImpl.class);
  private final ShortURLRepository shortURLRepository;
  private final DomainRepository domainRepository;
  private final Hashids hashids;

  @Override
  @Transactional
  public ResponseEntity<ResponseDTO> constructShortURL(LongURLRequestDTO longURLRequestDTO) {

    if(longURLRequestDTO == null || StringUtils.isBlank(longURLRequestDTO.getLongURL())) {
      throw new IllegalArgumentException("Please provide required details");
    }

    LOGGER.info("inside service layer going to invoke @constructShortURL method");
    String domainName = UrlUtils.extractDomain(longURLRequestDTO.getLongURL());

    if(UrlUtils.BLACKLISTED_DOMAINS != null && UrlUtils.BLACKLISTED_DOMAINS.contains(domainName)) {
      return ResponseEntity.badRequest().body(new ResponseDTO("BlackListed domain"));
    }

    String longUrlHash = HashUtils.sha256(longURLRequestDTO.getLongURL());

    LOGGER.info("longUrlHash: {}, domainName: {}",longUrlHash, domainName);
    Optional<ShortURL> shortURLEntity = shortURLRepository.findByLongUrlHash(longUrlHash);
    if(shortURLEntity.isPresent()) {
      LOGGER.info("short code is available {}",shortURLEntity.get().getShortCode());
      return ResponseEntity.ok(new ResponseDTO(shortURLEntity.get().getShortCode()));
    }
    Domain domainEntity = null;
    try {
      domainEntity = getDomain(domainName);
    } catch (Exception e) {
      LOGGER.error("Error occurred while inserting data in db: {}", ExceptionUtils.getStackTrace(e));
      throw new RuntimeException(e);
    }
    ShortURL shortURL = new ShortURL();
    shortURL.setLongUrl(longURLRequestDTO.getLongURL());
    shortURL.setLongUrlHash(longUrlHash);
    shortURL.setCreatedAt(LocalDateTime.now());
    shortURL.setDomain(domainEntity);
    ShortURL shortURLEntityFromDB = shortURLRepository.save(shortURL);
    if(shortURLEntityFromDB != null && shortURLEntityFromDB.getId()>=1) {
      String shortCode = hashids.encode(shortURLEntityFromDB.getId());
      shortURLEntityFromDB.setShortCode(shortCode);
      ShortURL save = shortURLRepository.save(shortURLEntityFromDB);
      LOGGER.info("Short URL created successfully");
      return ResponseEntity.ok(new ResponseDTO(shortCode));
    } else {
      LOGGER.info("Not able to create Short URL");
      throw new RuntimeException("Not able to create Short URL");
    }
  }

  @Override
  public ResponseEntity<ResponseDTO> getLongURL(String shortCode) {
    Validate.isTrue(StringUtils.isNotBlank(shortCode), "Please provide short code");
//    Validate.isTrue(shortCode.trim().length()==7, "Please provide validate shortcode");
    Optional<String> longURL = shortURLRepository.findByShortCode(shortCode);
    if(longURL.isPresent()) {
      HttpHeaders headers = new HttpHeaders();
      // Set the Location header with the long URL
      try {
        headers.setLocation(new URI(longURL.get()));
      } catch (URISyntaxException e) {
        throw new RuntimeException("Not able to redirect");
      }
      return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    } else {
      return ResponseEntity.badRequest().body(new ResponseDTO("Long URL not exists"));
    }
  }

  private @NonNull Domain getDomain(String domainName) {
    Optional<Domain> domainEntityOptional = domainRepository.findByDomainNameIgnoreCase(domainName);
    if(domainEntityOptional.isPresent()) {
      LOGGER.info("domain name is available");
      return domainEntityOptional.get();
    } else {
      LOGGER.info("domain name is not available");
      Domain domain = new Domain();
      domain.setDomainName(domainName);
      domain.setCreatedAt(LocalDateTime.now());
      try {
        Domain domainEntityFromDB = domainRepository.save(domain);
        LOGGER.info("domainEntityFromDB: {}", domainEntityFromDB);
        if(domainEntityFromDB != null &&  domainEntityFromDB.getId() >= 1) {
          return domainEntityFromDB;
        } else {
          throw new RuntimeException(String.format("Domain not created with domain name: %s", domainName));
        }
      }catch (Exception ex) {
        LOGGER.error("Exception occured while going to persisting data in db: cause: {}", ExceptionUtils.getStackTrace(ex));
        throw ex;
      }

    }
  }
}
