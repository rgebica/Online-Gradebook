package com.index.controller;

import com.index.dto.AddUserToSubjectDto;
import com.index.dto.CreateSubjectDto;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class SubjectController {

    SubjectService subjectService;

    @CrossOrigin
    @PostMapping("/subjects")
    public ResponseEntity<String> createSubject(@RequestBody CreateSubjectDto createSubjectDto) {
        subjectService.createSubject(createSubjectDto);
        return new ResponseEntity<>("Subject Created", OK);
    }

    @CrossOrigin
    @PostMapping("/user-Subjects")
    public ResponseEntity<String> addUserToSubject(@RequestBody AddUserToSubjectDto addUserToSubjectDto) {
        subjectService.addUserToSubject(addUserToSubjectDto);
        return new ResponseEntity<>("User added to subject", OK);
    }
}
