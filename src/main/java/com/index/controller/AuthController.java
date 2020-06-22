package com.index.controller;

import com.index.dto.*;
import com.index.service.AuthService;
import com.index.service.ClassService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;
    ClassService classService;

    public AuthController(AuthService authService, ClassService classService) {
        this.authService = authService;
        this.classService = classService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("/Users/{classId}")
    public ResponseEntity<List<ClassUsersDetailsDto>> getStudenstFromClass(@PathVariable long classId) {
        final List<ClassUsersDetailsDto> students = classService.getUsersByClassId(classId);
        return ResponseEntity.ok(students);
    }
}

