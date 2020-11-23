package com.index.controller;

import com.index.dto.*;
import com.index.service.*;
import com.index.service.serviceImpl.AuthServiceImpl;
import com.index.service.serviceImpl.ClassServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {

    AuthServiceImpl authService;
    ClassServiceImpl classService;
    SubjectService subjectService;
    RefreshTokenService refreshTokenService;
    UserInformationService userInformationService;
    UserResultsService userResultsService;
    ParentService parentService;
    UserService userService;


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

    @GetMapping("/user-information/{userId}")
    public UserPersonalInformationDto getInformation(@PathVariable long userId) {
        return userInformationService.getUserInformation(userId);
    }

    @GetMapping("/user-results/{userId}")
    public UserResultsDto getUserResults(@PathVariable long userId) {
        return userResultsService.getUserResults(userId);
    }

    @GetMapping("/parent-children/{userId}")
    public ParentChildrenDto getParentInformation(@PathVariable long userId) {
        return parentService.getParentPersonalInformation(userId);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>("User Created", OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}

