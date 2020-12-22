package com.index.controller;

import com.index.dto.AddClassDto;
import com.index.dto.AddUsersToClassDto;
import com.index.dto.ClassUsersDetailsDto;
import com.index.service.ClassService;
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
public class ClassController {

    ClassService classService;

    @CrossOrigin
    @PostMapping("/add-user-to-class")
    public ResponseEntity<String> addUserToClass(@RequestBody AddUsersToClassDto addUsersToClassDto) {
        classService.addUserToClass(addUsersToClassDto);
        return new ResponseEntity<>("User added to class", OK);
    }

    @CrossOrigin
    @PostMapping("/create-class")
    public ResponseEntity<String> createClass(@RequestBody AddClassDto addClassDto) {
        classService.addClass(addClassDto);
        return new ResponseEntity<>("Class created", OK);
    }

    @CrossOrigin
    @GetMapping("/users/{classId}")
    public ResponseEntity<List<ClassUsersDetailsDto>> getStudentsFromClass(@PathVariable long classId) {
        final List<ClassUsersDetailsDto> students = classService.getUsersByClassId(classId);
        return ResponseEntity.ok(students);
    }
}
