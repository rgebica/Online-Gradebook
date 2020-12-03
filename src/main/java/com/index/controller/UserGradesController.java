package com.index.controller;

import com.index.dto.AddGradeDto;
import com.index.dto.CreateUserDto;
import com.index.dto.GradeDto;
import com.index.dto.UserSubjectsGradesDetailsDto;
import com.index.service.GradeService;
import com.index.service.SubjectService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/auth")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserGradesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGradesController.class);

    GradeService gradeService;
    SubjectService subjectService;

    @CrossOrigin
    @PostMapping("/grades")
    public ResponseEntity<String> addGrade(@RequestBody AddGradeDto addGradeDto) {
        gradeService.addGrade(addGradeDto);
        return new ResponseEntity<>("Grade Added", OK);
    }

    @CrossOrigin
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

    @CrossOrigin
    @DeleteMapping(value = "/deleteUsers/{gradeIds}")
    public ResponseEntity<Void> deleteMovies(@PathVariable String gradeIds) {
        LOGGER.info("delete grades: {}", gradeIds);

        gradeService.deleteGradesByIds(gradeIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
