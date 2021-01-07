package com.index.dto;


import com.index.model.Grade;
import com.index.model.Subject;
import com.index.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsersSubjectGradesDetailsDto {

    String subjectName;
    String firstName;
    String lastName;
    List<GradeDto> grades;
    double subjectAverage;
    double semesterSubjectAverage;
    double yearAverage;
    Integer semesterGrade;
    Integer finalGrade;

    public static UsersSubjectGradesDetailsDto from(String subjectName, String firstName, String lastName, double subjectAverage,
                                                    List<GradeDto> grades, double semesterSubjectAverage, double yearAverage,
                                                    Integer semesterGrade, Integer finalGrade) {
        return UsersSubjectGradesDetailsDto.builder()
                .subjectName(subjectName)
                .firstName(firstName)
                .lastName(lastName)
                .subjectAverage(subjectAverage)
                .semesterSubjectAverage(semesterSubjectAverage)
                .semesterGrade(semesterGrade)
                .yearAverage(yearAverage)
                .finalGrade(finalGrade)
                .grades(grades)
                .build();
    }
}
