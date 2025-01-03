package com.example.avsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AvsoftBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvsoftBackendApplication.class, args);
	}

}
