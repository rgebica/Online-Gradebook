package com.index.service;


import lombok.Builder;

@Builder
public class GradeDetailsDto {

    private final long gradeId;
    private final long subjectId;
}
