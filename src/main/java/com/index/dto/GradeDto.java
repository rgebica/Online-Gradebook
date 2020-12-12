package com.index.dto;

import lombok.*;

@Getter
@Setter
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
    private long addedBy;
}
