package com.index.controller;

import com.index.dto.CreateBehaviourResponse;
import com.index.service.ParentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ParentController {

    ParentService parentService;

    @CrossOrigin
    @PostMapping("/add-Response")
    public ResponseEntity<String> addResponse(@RequestBody CreateBehaviourResponse createBehaviourResponse) {
        parentService.createResponseToBehaviour(createBehaviourResponse);
        return new ResponseEntity<>("Response sent", OK);
    }
}
