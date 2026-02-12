FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY build/libs/url-shortener-1.0.0-plain.jar url-shortener-1.0.0.jar
COPY build/libs/lib lib/
COPY external-config/ external-config/

ENTRYPOINT ["java","-cp","url-shortener-1.0.0.jar:lib/*","-Dspring.config.location=file:./external-config/","com.io.infracloud.urlshortener.URLShortenerApplication"]