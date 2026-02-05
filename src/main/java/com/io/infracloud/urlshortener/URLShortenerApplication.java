package com.io.infracloud.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class URLShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(URLShortenerApplication.class, args);
	}

}
