package com.index.controller;

import com.index.dto.AddBehaviourDto;
import com.index.dto.EditBehaviourDto;
import com.index.dto.EditPasswordDto;
import com.index.dto.UserBehaviourDetailsDto;
import com.index.service.BehaviourService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserBehaviourController {

    BehaviourService behaviourService;

    @CrossOrigin
    @PostMapping("/add-behaviour")
    public ResponseEntity<String> addBehaviour(@RequestBody AddBehaviourDto addBehaviourDto) {
        behaviourService.addBehaviour(addBehaviourDto);
        return new ResponseEntity<>("Behaviour Added", OK);
    }

    @CrossOrigin
    @GetMapping("/user-behaviours/{userId}")
    public ResponseEntity<List<UserBehaviourDetailsDto>> getUserBehaviour(@PathVariable long userId) {
        final List<UserBehaviourDetailsDto> userBehaviours = Collections.singletonList(behaviourService.getUserBehaviours(userId));
        return ResponseEntity.ok(userBehaviours);
    }

    @CrossOrigin
    @PutMapping("/behaviour/{behaviourId}")
    public ResponseEntity<String> editBehaviour(@RequestBody EditBehaviourDto editBehaviourDto, @PathVariable long behaviourId) {
        behaviourService.editBehaviour(editBehaviourDto, behaviourId);
        return new ResponseEntity<>("Behaviour edited", OK);
    }
}
