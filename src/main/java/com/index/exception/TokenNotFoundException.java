package com.index.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException (String token) {
        super("Could not find user with id: " + token);
    }
}
