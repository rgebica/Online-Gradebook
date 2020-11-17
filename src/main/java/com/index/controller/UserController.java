package com.index.controller;

import com.index.dto.*;
import com.index.exception.LoginCredentialsAdvice;
import com.index.exception.LoginCredentialsException;
import com.index.service.RefreshTokenService;
import com.index.service.UserInformationService;
import com.index.service.UserService;
import com.index.service.serviceImpl.ClassServiceImpl;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {

    UserService userService;
    ClassServiceImpl classService;
    SubjectService subjectService;
    RefreshTokenService refreshTokenService;
    UserInformationService userInformationService;


    @GetMapping("account-Verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/users/{classId}")
    public ResponseEntity<List<ClassUsersDetailsDto>> getStudentsFromClass(@PathVariable long classId) {
        final List<ClassUsersDetailsDto> students = classService.getUsersByClassId(classId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/subjects/{userId}")
    public ResponseEntity<UserSubjectsDetailsDto> getSubjectsByUserId(@PathVariable long userId) {
        final UserSubjectsDetailsDto subjects = subjectService.getSubjectsByUserId(userId);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }

    @GetMapping("/user-information/{userId}")
    public UserPersonalInformation getInformation(@PathVariable long userId) {
        return userInformationService.getUserInformation(userId);
    }
}

