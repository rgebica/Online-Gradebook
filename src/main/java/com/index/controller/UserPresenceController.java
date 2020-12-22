package com.index.controller;

import com.index.dto.*;
import com.index.service.serviceImpl.PresenceServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserPresenceController {

    PresenceServiceImpl presenceService;

    @CrossOrigin
    @PostMapping("/add-presence")
    public ResponseEntity<String> addGrade(@RequestBody AddPresenceDto addPresenceDto) {
        presenceService.addPresenceDto(addPresenceDto);
        return new ResponseEntity<>("Presence Added", OK);
    }

    @CrossOrigin
    @GetMapping("/presence/{userId}/subjects")
    public ResponseEntity<List<UserPresenceDetailsDto>> getPresenceById(@PathVariable long userId) {
        final List<UserPresenceDetailsDto> userSubjects = presenceService.getPresenceByUserId(userId);
        return ResponseEntity.ok(userSubjects);
    }

    @CrossOrigin
    @PutMapping("/presence/{presenceId}")
    public ResponseEntity<String> editPresence(@RequestBody EditPresenceDto editPresenceDto, @PathVariable long presenceId) {
        presenceService.editPresence(editPresenceDto, presenceId);
        return new ResponseEntity<>("Presence edited", OK);
    }
}
