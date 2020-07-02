package com.index.controller;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import com.index.dto.UserSubjectsGradesDetailsDto;
import com.index.service.ClassService;
import com.index.service.GradeService;
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

    @Autowired
    public GradeController(GradeService gradeService, SubjectService subjectService) {
        this.gradeService = gradeService;
        this.subjectService = subjectService;
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

    @GetMapping("/{userId}/subjects")
    public ResponseEntity<List<UserSubjectsGradesDetailsDto>> getUserSubjectsWithGrades(@PathVariable long userId) {
        final List<UserSubjectsGradesDetailsDto> userSubjects = subjectService.getUserSubjectsWithGrades(userId);
        return ResponseEntity.ok(userSubjects);
    }
}
