package com.io.infracloud.urlshortener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.io.infracloud.urlshortener.dto.MatrixResponseDTO;
import com.io.infracloud.urlshortener.entity.ShortURL;

public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {

  Optional<ShortURL> findByLongUrlHash(String longUrlHash);

  @Query("""
      select new com.io.infracloud.urlshortener.dto.MatrixResponseDTO(d.domainName,count(s.id)) 
      from ShortURL s join s.domain d
      group by d.id, d.domainName
      order by count(s.id) desc
      """)
  List<MatrixResponseDTO> findTopDomains(Pageable pageable);

  @Query("select s.longUrl from ShortURL s where s.shortCode=:shortCode")
  Optional<String> findByShortCode(String shortCode);
}
