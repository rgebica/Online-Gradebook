package com.index.controller;

import com.index.domain.dto.TeacherDto;
import com.index.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value="/api")
public class TeacherController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @CrossOrigin
    @GetMapping(value = "/teachers", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<TeacherDto>> getTeachers() {
        LOGGER.info("Find all teachers");

        List<TeacherDto> teacherDto = teacherService.findAll();
        return new ResponseEntity<>(teacherDto, HttpStatus.OK);
    }
}
