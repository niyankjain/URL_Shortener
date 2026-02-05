package com.io.infracloud.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.infracloud.urlshortener.entity.ShortURL;

public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {

  Optional<ShortURL> findByLongUrlHash(String longUrlHash);
}
