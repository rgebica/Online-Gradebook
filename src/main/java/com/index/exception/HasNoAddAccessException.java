package com.index.exception;

public class HasNoAddAccessException extends RuntimeException {
    public HasNoAddAccessException () {
        super("You have not permission to add this grade ");
    }
}
