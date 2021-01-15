package com.index.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class PresenceNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PresenceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String presenceNotFoundHandler (PresenceNotFoundException ex) {
        return ex.getMessage();
    }
}
