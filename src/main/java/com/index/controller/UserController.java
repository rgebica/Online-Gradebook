package com.index.controller;

import com.index.dto.*;
import com.index.service.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {

    UserInformationService userInformationService;
    UserResultsService userResultsService;
    ParentService parentService;
    UserService userService;

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
        return new ResponseEntity<>("User Created", CREATED);
    }

    @CrossOrigin
    @DeleteMapping("/user/{userIds}")
    public ResponseEntity<Void> deleteUsers(@PathVariable String userIds) {
        LOGGER.info("delete users: {}", userIds);

        userService.deleteUsersByIds(userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/user-info/{userId}")
    public ResponseEntity<String> editBasicInfo(@RequestBody UserEditInfoDto userEditInfoDto, @PathVariable long userId) {
        userService.editBasicInfo(userEditInfoDto, userId);
        return new ResponseEntity<>("User password edited", OK);
    }

    @CrossOrigin
    @GetMapping("/students")
    public ResponseEntity<List<StudentsDto>> getStudents() {
        final List<StudentsDto> students = Collections.singletonList(userService.getAllStudents());
        return ResponseEntity.ok(students);
    }

    @CrossOrigin
    @PutMapping("/user-password/{userId}")
    public ResponseEntity<String> editPassword(@RequestBody EditPasswordDto editPasswordDto, @PathVariable long userId) {
        userService.editPassword(editPasswordDto, userId);
        return new ResponseEntity<>("User password edited", OK);
    }
    @CrossOrigin
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        final List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @CrossOrigin
    @GetMapping("/all-teachers")
    public ResponseEntity<List<UserDto>> getAllTeachers() {
        final List<UserDto> teachers = userService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @CrossOrigin
    @GetMapping("/all-parents")
    public ResponseEntity<List<UserDto>> getAllParents() {
        final List<UserDto> parents = userService.getAllParents();
        return ResponseEntity.ok(parents);
    }
}

