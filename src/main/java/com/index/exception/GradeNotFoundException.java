package com.index.exception;

public class GradeNotFoundException extends RuntimeException{
    public GradeNotFoundException (long gradeId) {
        super("Could not find grade with id: " + gradeId);
    }
}
