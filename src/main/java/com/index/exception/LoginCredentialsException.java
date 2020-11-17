package com.index.exception;

public class LoginCredentialsException extends RuntimeException{
    public LoginCredentialsException (String username, String password) {
        super("Username" + username + "or password " + password + "is incorrect");
    }
}
