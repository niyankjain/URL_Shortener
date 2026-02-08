# URL Shortener Service

A scalable and production-ready **URL Shortener** built using **Spring Boot** that supports secure short code generation, domain-based analytics, and application monitoring.

---

## 📌 Project Overview

This project provides a REST-based URL shortening service similar to Bitly/TinyURL.  
It converts long URLs into short, fixed-length codes and supports **domain-level analytics** to identify the most frequently shortened domains.

Key goals of the project:
- Collision-free short URL generation
- Secure, non-sequential short codes
- Clean REST API design
- Database-backed persistence
- Analytics using aggregation queries
- Production-grade monitoring

---

## ✨ Features

- 🔗 **URL Shortening**
  - Convert long URLs into 7-character short codes
  - Hashids-based obfuscation (no primary key exposure)

- 📊 **Domain-Based Analytics**
  - Track how many URLs are shortened per domain
  - Fetch top N domains by usage

- 🛡 **Validation & Error Handling**
  - Request validation using Jakarta Bean Validation
  - Centralized exception handling

- 🗄 **Persistence**
  - MySQL database
  - JPA / Hibernate ORM

- 📈 **Monitoring**
  - JavaMelody integration for:
    - HTTP request metrics
    - JVM statistics
    - SQL performance
    - Error tracking

---

## 🧱 Architecture
Client
│
▼
REST Controller
│
▼
Service Layer
│
▼
Repository (Spring Data JPA)
│
▼
MySQL Database
