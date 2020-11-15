package com.index.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddGradeDto {
    long subjectId;
    long userId;
    Integer grade;
    Integer gradeWeight;
    String comment;
    String addedBy;
}