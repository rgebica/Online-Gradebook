package com.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexApplication.class, args);
	}

}