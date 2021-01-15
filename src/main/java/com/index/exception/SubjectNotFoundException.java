package com.index.exception;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException (long subjectId) {
        super("Could not find subject with id: " + subjectId);
    }
}
