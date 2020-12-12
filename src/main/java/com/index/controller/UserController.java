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
    @GetMapping("/users/{classId}")
    public ResponseEntity<List<ClassUsersDetailsDto>> getStudentsFromClass(@PathVariable long classId) {
        final List<ClassUsersDetailsDto> students = classService.getUsersByClassId(classId);
        return ResponseEntity.ok(students);
    }

    @CrossOrigin
    @GetMapping("/subjects/{userId}")
    public ResponseEntity<UserSubjectsDetailsDto> getSubjectsByUserId(@PathVariable long userId) {
        final UserSubjectsDetailsDto subjects = subjectService.getSubjectsByUserId(userId);
        return ResponseEntity.ok(subjects);
    }

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
    @DeleteMapping(value = "/deleteUsers/{userIds}")
    public ResponseEntity<Void> deleteMovies(@PathVariable String userIds) {
        LOGGER.info("delete users: {}", userIds);

        userService.deleteUsersByIds(userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUserBasicInfo(@PathVariable long userId, @RequestBody UserPersonalInformationDto user) {
        Optional<User> userData = userRepository.findById(userId);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setRole(user.getRole());
            _user.setEmail(user.getEmail());

            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudents() {
        final List<StudentDto> students = Collections.singletonList(userService.getAllStudents());
        return ResponseEntity.ok(students);
    }

    @CrossOrigin
    @PostMapping("/add-Response")
    public ResponseEntity<String> addResponse(@RequestBody CreateBehaviourResponse createBehaviourResponse) {
        parentService.createResponseToBehaviour(createBehaviourResponse);
        return new ResponseEntity<>("Response sent", OK);
    }

    @CrossOrigin
    @PostMapping("/add-User-To-Class")
    public ResponseEntity<String> addUserToClass(@RequestBody AddUsersToClassDto addUsersToClassDto) {
        classService.addUserToClass(addUsersToClassDto);
        return new ResponseEntity<>("User added to class", OK);
    }

    @CrossOrigin
    @PostMapping("/add-Class")
    public ResponseEntity<String> addClass(@RequestBody AddClassDto addClassDto) {
        classService.addClass(addClassDto);
        return new ResponseEntity<>("Class created", OK);
    }
}

