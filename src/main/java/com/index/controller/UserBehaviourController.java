package com.index.controller;

import com.index.dto.AddBehaviourDto;
import com.index.dto.BehaviourDto;
import com.index.dto.UserBehaviourDetailsDto;
import com.index.service.BehaviourService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserBehaviourController {

    BehaviourService behaviourService;

    @PostMapping("/addBehaviour")
    public ResponseEntity<BehaviourDto> addGrade(@RequestBody AddBehaviourDto addBehaviour) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(behaviourService.addBehaviour(addBehaviour));
    }

    @GetMapping("/behaviour/{userId}")
    public ResponseEntity<List<UserBehaviourDetailsDto>> getUserBehaviour(@PathVariable long userId) {
        final List<UserBehaviourDetailsDto> userBehaviours = Collections.singletonList(behaviourService.getUserBehaviours(userId));
        return ResponseEntity.ok(userBehaviours);
    }
}
