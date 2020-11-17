package com.index.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LoginCredentialsAdvice {
    @ResponseBody
    @ExceptionHandler(LoginCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String loginCredentialsHandler (LoginCredentialsException ex) {return ex.getMessage();
    }
}
