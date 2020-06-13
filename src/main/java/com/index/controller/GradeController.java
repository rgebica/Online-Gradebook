package com.index.controller;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import com.index.dto.UserDto;
import com.index.service.GradeService;
import com.index.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/grade")
@AllArgsConstructor
@Slf4j
public class GradeController {

    private final GradeService gradeService;

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
}
