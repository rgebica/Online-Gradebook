package com.index.controller;

import com.index.domain.dto.CreateStudentDto;
import com.index.domain.dto.StudentDto;
import com.index.domain.dto.TeacherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.index.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/api")
public class StudentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @CrossOrigin
    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<StudentDto>> getUsers() {
        LOGGER.info("Find all users");

        List<StudentDto> usersDto = studentService.findAll();
        return new ResponseEntity<>(usersDto,HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody CreateStudentDto createStudentDto) {
        LOGGER.info("create movie: {}", createStudentDto);

        studentService.createUser(createStudentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
