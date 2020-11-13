package com.index.controller;

import com.index.dto.CreateUserDto;
import com.index.service.AdminService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AdminController {

    AdminService adminService;

    @PostMapping("/users")
    public ResponseEntity<String> signup(@RequestBody CreateUserDto createUserDto) {
        adminService.createUser(createUserDto);
        return new ResponseEntity<>("User Created",
                OK);
    }
}
