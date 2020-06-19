package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeDto {
    private long userId;
    private long gradeId;
    private long subjectId;
    private int grade;
    private Instant date;
    private String comment;
}
