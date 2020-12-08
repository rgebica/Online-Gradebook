package com.index.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class StartController {

    @CrossOrigin
    @GetMapping
    public ResponseEntity<String> starter() {
        return new ResponseEntity<>("Starter Page", OK);
    }
}
