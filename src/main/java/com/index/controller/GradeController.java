package com.index.controller;

import com.index.dto.AddGradeDto;
import com.index.dto.ClassDto;
import com.index.dto.GradeDto;
import com.index.dto.UserSubjectsDetailsDto;
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
@RequestMapping("api/grade")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GradeController {

    GradeService gradeService;
    SubjectService subjectService;
    ClassService classService;

    @Autowired
    public GradeController(GradeService gradeService, SubjectService subjectService, ClassService classService) {
        this.gradeService = gradeService;
        this.subjectService = subjectService;
        this.classService = classService;
    }

    @PostMapping("/addGrade")
    public ResponseEntity<GradeDto> addGrade(@RequestBody AddGradeDto addGradeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(gradeService.addGrade(addGradeDto));
    }

    @GetMapping("/UserGrades/{userId}")
    public ResponseEntity<List<GradeDto>> getAllGrades(@PathVariable long userId) {
        final List<GradeDto> grades = gradeService.getGradesByUser(userId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{userId}/subjects")
    public ResponseEntity<List<UserSubjectsDetailsDto>> getUserSubjectsWithGrades(@PathVariable long userId) {
        final List<UserSubjectsDetailsDto> userSubjects = subjectService.getUserSubjectsWithGrades(userId);
        return ResponseEntity.ok(userSubjects);
    }
}
