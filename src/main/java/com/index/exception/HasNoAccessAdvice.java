package com.index.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class HasNoAccessAdvice {
    @ResponseBody
    @ExceptionHandler(HasNoAddAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String hasNoAddAccessHandler (HasNoAddAccessException ex) {
        return ex.getMessage();
    }
}
