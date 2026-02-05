package com.io.infracloud.urlshortener.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="short_urls")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShortURL {

  @Id
  @Column(name="id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name="long_url")
  private String longUrl;

  @Column(name="shortCode")
  private String shortCode;

  @Column(name="long_url_hash")
  private String longUrlHash;

  @Column(name ="created_at")
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="domain_id", nullable = false)
  private Domain domain;
}
