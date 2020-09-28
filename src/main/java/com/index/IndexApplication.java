package com.index;

import com.index.config.SwaggerConfiguration;
import com.index.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class IndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndexApplication.class, args);
        String pass = UserService.generateParentCode();
        System.out.println(pass);
    }
}