package com.index.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BehaviourNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BehaviourNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String behaviourNotFoundHandler (BehaviourNotFoundException ex) {
        return ex.getMessage();
    }
}
