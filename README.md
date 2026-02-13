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
  - H2 database
  - JPA / Hibernate ORM

- 📈 **Monitoring**
  - JavaMelody integration for:
    - HTTP request metrics
    - JVM statistics 
    - SQL performance
    - Error tracking

---

## 🧱 Architecture

The URL Shortener follows a **layered Spring Boot architecture** with clear separation of concerns.

### High-Level Flow
Client
│
▼
REST Controller
│
▼
Service Layer
│
▼
Repository (JPA)
│
▼
MySQL Database
---

## 🚀 Quick Start

### Prerequisites
- **Java 17+** installed
- **Gradle 4+** installed
- **H2 Database** (H2 is configured by default for development)

### Application Configuration
| Property | Value | Description |
|----------|-------|-------------|
| **Server Port** | `9091` | Application running port |
| **Context Path** | `/url-shortener` | Application base path |
| **Base URL** | `http://localhost:9091/url-shortener/` | Main application URL |
| **H2 Console** | `http://localhost:9091/url-shortener/h2-console` | Database access (dev only) |
| **Java Melody** | `http://localhost:9091/url-shortener/monitoring` | Performance monitoring |

### Running the Application

#### Option 1: Using Gradle
```bash
# Clone the repository
git clone https://github.com/niyankjain/URL_Shortener.git
cd url-shortener
git checkout H2-database

# Build and run
./gradlew clean build
./gradlew bootRun
```

#### Option 2: Using JAR
```bash
# Build the application
./gradlew build

# Run the JAR file
java -jar build/libs/url-shortener-*.jar
```

#### Option 3: Using IDE
- Import the project as a Gradle project
- Run `URLShortenerApplication.java` as a Java application

### Verification
Once the application starts successfully, you should see:
```
Started URLShortenerApplication in X.XXX seconds
Tomcat started on port(s): 9091 (http)
```

Test the application by accessing:
- **Health Check**: `http://localhost:9091/url-shortener/actuator/health`
- **API Docs**: `http://localhost:9091/url-shortener/api/v1/constructShortURL` (POST)

---
## 🚀 API Endpoints

### 1. Create Short URL
**POST** `/api/v1/constructShortURL`

Creates a short URL from a long URL.

#### Request
```json
{
  "longURL": "https://www.example.com/very/long/url/that/needs/to/be/shortened"
}
```

#### Response
```json
{
  "message": "abc1234"
}
```

#### cURL Example
```bash
curl -X POST http://localhost:9091/url-shortener/api/v1/constructShortURL \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "longURL": "https://www.example.com/very/long/url/that/needs/to/be/shortened"
  }'
```

---

### 2. Redirect to Long URL
**GET** `/api/{shortCode}`

Redirects to the original long URL using the short code.

#### Parameters
- `shortCode` (path): The short code generated when creating the short URL

#### Response
```json
{
  "message": "https://www.example.com/very/long/url/that/needs/to/be/shortened"
}
```

#### cURL Example
```bash
curl -X GET http://localhost:9091/url-shortener/api/abc1234
```

---

### 3. Get Top Domain Analytics
**GET** `/api/v1/fetchTopDomainMatrix`

Retrieves analytics showing the most frequently shortened domains.

#### Response
```json
[
  {
    "domain": "example.com",
    "urlCount": 25
  },
  {
    "domain": "google.com",
    "urlCount": 18
  },
  {
    "domain": "github.com",
    "urlCount": 12
  }
]
```

#### cURL Example
```bash
curl -X GET http://localhost:9091/url-shortener/api/v1/fetchTopDomainMatrix
```

---

## 📋 HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| `200 OK` | Request successful |
| `201 Created` | Resource created successfully |
| `400 Bad Request` | Invalid request data |
| `404 Not Found` | Short code not found |
| `500 Internal Server Error` | Server error |

---

## 🧪 Testing the API

### Using Postman
1. Import the following collection:
   - Base URL: `http://localhost:9091/url-shortener`
   - Endpoints as described above

### Using HTTP Client
```bash
# Test all endpoints
echo "Testing URL Shortener API..."

# Create short URL
echo "1. Creating short URL..."
curl -X POST http://localhost:9091/url-shortener/api/v1/constructShortURL \
  -H "Content-Type: application/json" \
  -H "Accept: application/json"
  -d '{"longURL": "https://www.github.com/test-repo"}'

# Get domain analytics
echo -e "\n2. Getting domain analytics..."
curl -X GET http://localhost:9091/url-shortener/api/v1/fetchTopDomainMatrix
```
 

## 📁 Package & File Structure
```text
src/main/java/com/io/infracloud/urlshortener
├── controller
│   ├── impl
│   │   ├── ShortURLRestImpl.java
│   │   └── DomainRestImpl.java
│   └── api
│       ├── ShortURLRest.java
│       └── DomainRest.java
│
├── service
│   ├── impl
│   │   ├── ShortURLServiceImpl.java
│   │   └── DomainServiceImpl.java
│   └── api
│       ├── ShortURLService.java
│       └── DomainService.java
│
├── repository
│   ├── ShortURLRepository.java
│   └── DomainRepository.java
│
├── entity
│   ├── ShortURL.java
│   └── Domain.java
│
├── dto
│   ├── ErrorResponseDTO.java
│   ├── LongURLRequestDTO.java
│   ├── ResponseDTO.java
│   └── MatrixResponseDTO.java
│
├── exception
│   └── handler
│       └── CustomExceptionHandler.java
│
├── config
│   └── HashidsConfig.java
│
├── utils
│   ├── HashUtils.java
│   ├── URLShortenerConstant.java
│   └── UrlUtils.java
│
└── UrlShortenerApplication.java
```

## 🏗️ System Architecture

The application follows a **layered architecture pattern** with clear separation of concerns:

### Core Layers
- **Presentation Layer**: REST Controllers handle HTTP requests and responses
- **Business Layer**: Services contain business logic and validation
- **Data Access Layer**: JPA Repositories manage database operations
- **Database Layer**: MySQL for persistent storage

### Key Design Patterns
- **Repository Pattern**: For data access abstraction
- **DTO Pattern**: For data transfer between layers
- **Service Layer Pattern**: For business logic encapsulation
- **Controller Pattern**: For REST API endpoints

### Security & Validation
- **Bean Validation**: Jakarta validation for request validation
- **Global Exception Handling**: Centralized error processing
- **Hash-based Encoding**: Secure short code generation using Hashids

---
