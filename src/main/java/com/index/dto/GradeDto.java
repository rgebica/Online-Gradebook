package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeDto {
    private long userId;
    private long gradeId;
    private long subjectId;
    private int grade;
    private int gradeWeight;
    private String date;
    private String comment;
}
