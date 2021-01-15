package com.index.service;

public interface AccessSecurityService{
    void checkHasAddAccess();

    void checkAddGradeToStudent(long userId);

    void checkIfSubjectExists(long subjectId);
}
