package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SemesterGradeDto {
    private long gradeId;
    private double subjectAverage;
    private Integer finalGrade;
    private String semester;
    private long userId;
    private long subjectId;
}
