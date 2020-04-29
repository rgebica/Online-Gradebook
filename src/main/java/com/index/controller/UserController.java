package com.index.controller;

import com.index.domain.dto.CreateUserDto;
import com.index.domain.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.index.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/api")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDto>> getUsers() {
        LOGGER.info("Find all users");

        List<UserDto> usersDto = userService.findAll();
        return new ResponseEntity<>(usersDto,HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        LOGGER.info("create movie: {}", createUserDto);

        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
