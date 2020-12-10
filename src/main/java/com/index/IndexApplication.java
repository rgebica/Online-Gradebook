package com.index;

import com.index.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Import(SwaggerConfiguration.class)
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class IndexApplication{

    public static void main(String[] args) {
        SpringApplication.run(IndexApplication.class, args);
    }
}