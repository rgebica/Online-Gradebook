package com.index.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (long userId) {
        super("Could not find user with id: " + userId);
    }
}
