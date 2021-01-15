package com.index.exception;

public class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException (long classId) {
        super("Could not find class with id: " + classId);
    }
}
