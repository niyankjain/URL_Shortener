package com.io.infracloud.urlshortener.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hashids.Hashids;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.io.infracloud.urlshortener.dto.LongURLRecord;
import com.io.infracloud.urlshortener.dto.ResponseDTO;
import com.io.infracloud.urlshortener.entity.Domain;
import com.io.infracloud.urlshortener.entity.ShortURL;
import com.io.infracloud.urlshortener.repository.DomainRepository;
import com.io.infracloud.urlshortener.repository.ShortURLRepository;
import com.io.infracloud.urlshortener.service.ShortURLService;
import com.io.infracloud.urlshortener.utils.HashUtils;
import com.io.infracloud.urlshortener.utils.UrlUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ShortURLServiceImpl implements ShortURLService {

  private final static Logger LOGGER = LoggerFactory.getLogger(ShortURLServiceImpl.class);
  private final ShortURLRepository shortURLRepository;
  private final DomainRepository domainRepository;
  private final Hashids hashids;

  @Override
  @Transactional
  public ResponseEntity<ResponseDTO> constructShortURL(LongURLRecord longURLRecord) {

    LOGGER.info("inside service layer going to invoke @constructShortURL method");
    String longUrlHash = HashUtils.sha256(longURLRecord.longURL());
    String domainName = UrlUtils.extractDomain(longURLRecord.longURL());

    LOGGER.info("longUrlHash: {}, domainName: {}",longUrlHash, domainName);
    Optional<ShortURL> shortURLEntity = shortURLRepository.findByLongUrlHash(longUrlHash);
    if(shortURLEntity.isPresent()) {
      LOGGER.info("short code is available {}",shortURLEntity.get().getShortCode());
      return ResponseEntity.ok(new ResponseDTO(shortURLEntity.get().getShortCode()));
    }

    Domain domainEntity = getDomain(domainName);
    ShortURL shortURL = new ShortURL();
    shortURL.setLongUrl(longURLRecord.longURL());
    shortURL.setLongUrlHash(longUrlHash);
    shortURL.setCreatedAt(LocalDateTime.now());
    shortURL.setDomain(domainEntity);
    ShortURL shortURLEntityFromDB = shortURLRepository.save(shortURL);
    if(shortURLEntityFromDB != null && shortURLEntityFromDB.getId()>1) {

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
      Domain domainEntityFromDB = domainRepository.save(domain);
      if(domainEntityFromDB != null &&  domainEntityFromDB.getId() > 1) {
        return domainEntityFromDB;
      } else {
        throw new RuntimeException(String.format("Domain not created with domain name: %s", domainName));
      }
    }
  }
}
