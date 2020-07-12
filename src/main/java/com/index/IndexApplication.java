package com.index;

import com.index.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Instant;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class IndexApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndexApplication.class, args);
		System.out.println(Instant.now());
	}
}