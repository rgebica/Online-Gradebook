package com.index.controller;

import com.index.dto.*;
import com.index.service.serviceImpl.PresenceServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserPresenceController {

    PresenceServiceImpl presenceService;

    @PostMapping("/addPresence")
    public ResponseEntity<PresenceDto> addPresence(@RequestBody AddPresenceDto addPresence) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(presenceService.addPresenceDto(addPresence));
    }

    @GetMapping("/presence/{userId}/subjects")
    public ResponseEntity<List<UserPresenceDetailsDto>> getPresenceById(@PathVariable long userId) {
        final List<UserPresenceDetailsDto> userSubjects = presenceService.getPresenceByUserId(userId);
        return ResponseEntity.ok(userSubjects);
    }
}
