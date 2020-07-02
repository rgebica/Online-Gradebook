package com.index.controller;

import com.index.dto.*;
import com.index.service.UserService;
import com.index.service.ClassService;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;
    ClassService classService;
    SubjectService subjectService;

    public UserController(UserService userService, ClassService classService, SubjectService subjectService) {
        this.userService = userService;
        this.classService = classService;
        this.subjectService = subjectService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        userService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",
                OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        userService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/users/{classId}")
    public ResponseEntity<List<ClassUsersDetailsDto>> getStudenstFromClass(@PathVariable long classId) {
        final List<ClassUsersDetailsDto> students = classService.getUsersByClassId(classId);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/subjects/{userId}")
    public ResponseEntity<UserSubjectsDetailsDto> getSubjectsByUserId(@PathVariable long userId) {
        final UserSubjectsDetailsDto subjects = subjectService.getSubjectsByUserId(userId);
        return ResponseEntity.ok(subjects);
    }
}

