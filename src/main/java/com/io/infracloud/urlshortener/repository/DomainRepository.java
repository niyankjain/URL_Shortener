package com.io.infracloud.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.infracloud.urlshortener.entity.Domain;

public interface DomainRepository extends JpaRepository<Domain, Long> {
  Optional<Domain> findByDomainNameIgnoreCase(String domainName);
}
