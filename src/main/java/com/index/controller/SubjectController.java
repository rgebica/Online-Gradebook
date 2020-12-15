package com.index.controller;

import com.index.dto.*;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SubjectController {

    SubjectService subjectService;

    @CrossOrigin
    @PostMapping("/create-subject")
    public ResponseEntity<String> createSubject(@RequestBody CreateSubjectDto createSubjectDto) {
        subjectService.createSubject(createSubjectDto);
        return new ResponseEntity<>("Subject Created", OK);
    }

    @CrossOrigin
    @PostMapping("/user-subjects")
    public ResponseEntity<String> addUserToSubject(@RequestBody AddUserToSubjectDto addUserToSubjectDto) {
        subjectService.addUserToSubject(addUserToSubjectDto);
        return new ResponseEntity<>("Subjects added to user", OK);
    }

    @CrossOrigin
    @GetMapping("/subjects")
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @CrossOrigin
    @GetMapping("/student-subjects/{subjectId}")
    public List<UserDto> getSubjectUsers(@PathVariable long subjectId) {
        return subjectService.getUsersBySubjectId(subjectId);
    }

    @CrossOrigin
    @GetMapping("/subjects/{userId}")
    public ResponseEntity<UserSubjectsDetailsDto> getSubjectsByUserId(@PathVariable long userId) {
        final UserSubjectsDetailsDto subjects = subjectService.getSubjectsByUserId(userId);
        return ResponseEntity.ok(subjects);
    }
}
