package com.index.controller;

import com.index.dto.*;
import com.index.model.User;
import com.index.repository.UserRepository;
import com.index.service.*;
import com.index.service.serviceImpl.ClassServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {

    ClassServiceImpl classService;
    SubjectService subjectService;
    UserInformationService userInformationService;
    UserResultsService userResultsService;
    ParentService parentService;
    UserService userService;
    UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @CrossOrigin
    @GetMapping("/user-information/{userId}")
    public UserPersonalInformationDto getInformation(@PathVariable long userId) {
        return userInformationService.getUserInformation(userId);
    }

    @CrossOrigin
    @GetMapping("/user-results/{userId}")
    public UserResultsDto getUserResults(@PathVariable long userId) {
        return userResultsService.getUserResults(userId);
    }

    @CrossOrigin
    @GetMapping("/parent-children/{userId}")
    public ParentChildrenDto getParentInformation(@PathVariable long userId) {
        return parentService.getParentPersonalInformation(userId);
    }

    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>("User Created", OK);
    }

    @CrossOrigin
    @DeleteMapping(value = "/users/{userIds}")
    public ResponseEntity<Void> deleteMovies(@PathVariable String userIds) {
        LOGGER.info("delete users: {}", userIds);

        userService.deleteUsersByIds(userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/edit-Info/{userId}")
    public ResponseEntity<String> editBasicInfo(@RequestBody UserEditInfoDto userEditInfoDto, @PathVariable long userId) {
        userService.editBasicInfo(userEditInfoDto, userId);
        return new ResponseEntity<>("User password edited", OK);
    }

    @CrossOrigin
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudents() {
        final List<StudentDto> students = Collections.singletonList(userService.getAllStudents());
        return ResponseEntity.ok(students);
    }

    @CrossOrigin
    @PutMapping("/edit-Password/{userId}")
    public ResponseEntity<String> editPassword(@RequestBody EditPasswordDto editPasswordDto, @PathVariable long userId) {
        userService.editPassword(editPasswordDto, userId);
        return new ResponseEntity<>("User password edited", OK);
    }
}

