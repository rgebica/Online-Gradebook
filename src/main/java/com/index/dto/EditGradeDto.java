package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditGradeDto {
    long subjectId;
    long userId;
    Integer grade;
    Integer gradeWeight;
    String comment;
}