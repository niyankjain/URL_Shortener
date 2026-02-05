package com.io.infracloud.urlshortener;

import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.io.infracloud.urlshortener.utils.URLShortenerConstant;

@Configuration
public class HashidsConfig {

  @Bean
  public Hashids hashids() {
    return new Hashids(URLShortenerConstant.SALT, 7, URLShortenerConstant.BASE62);
  }
}
