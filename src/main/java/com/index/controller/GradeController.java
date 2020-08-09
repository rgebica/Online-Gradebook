package com.index.controller;

import com.index.dto.*;
import com.index.service.ClassService;
import com.index.service.GradeService;
import com.index.service.PresenceService;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GradeController {

    GradeService gradeService;
    SubjectService subjectService;
    PresenceService presenceService;

    @Autowired
    public GradeController(GradeService gradeService, SubjectService subjectService, PresenceService presenceService) {
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.presenceService = presenceService;
    }

    @PostMapping("/addGrade")
    public ResponseEntity<GradeDto> addGrade(@RequestBody AddGradeDto addGradeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.addGrade(addGradeDto));
    }

    @GetMapping("/userGrades/{userId}")
    public ResponseEntity<List<GradeDto>> getAllGrades(@PathVariable long userId) {
        final List<GradeDto> grades = gradeService.getGradesByUser(userId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/grades/{userId}/subjects")
    public ResponseEntity<List<UserSubjectsGradesDetailsDto>> getUserSubjectsWithGrades(@PathVariable long userId) {
        final List<UserSubjectsGradesDetailsDto> userSubjects = subjectService.getUserSubjectsWithGrades(userId);
        return ResponseEntity.ok(userSubjects);
    }

    @PostMapping("/addPresence")
    public ResponseEntity<PresenceDto> addPresence(@RequestBody AddPresenceDto addPresence) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.addPresenceDto(addPresence));
    }

    @GetMapping("/presence/{userId}/subjects")
    public ResponseEntity<List<UserPresenceDetailsDto>> getPresenceById(@PathVariable long userId) {
        final List<UserPresenceDetailsDto> userSubjects = presenceService.getPresenceByUserId(userId);
        return ResponseEntity.ok(userSubjects);
    }

    @PostMapping("/addBehaviour")
    public ResponseEntity<BehaviourDto> addGrade(@RequestBody AddBehaviourDto addBehaviour) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.addBehaviour(addBehaviour));
    }
}
