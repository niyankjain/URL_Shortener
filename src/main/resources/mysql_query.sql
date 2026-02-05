DROP DATABASE IF EXISTS URL_SHORTENER;
CREATE DATABASE URL_SHORTENER;

CREATE TABLE domains (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    domain_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_domains_domain_name (domain_name)
);

CREATE TABLE short_urls (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    domain_id BIGINT NOT NULL,
    short_code VARCHAR(16) NOT NULL,
    long_url TEXT NOT NULL,
    long_url_hash CHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE KEY uk_short_urls_short_code (short_code),
    KEY idx_short_urls_domain_id (domain_id),
    KEY idx_short_urls_long_url_hash (long_url_hash),

    CONSTRAINT fk_short_urls_domain
    FOREIGN KEY (domain_id)
    REFERENCES domains(id)
    ON DELETE RESTRICT
);