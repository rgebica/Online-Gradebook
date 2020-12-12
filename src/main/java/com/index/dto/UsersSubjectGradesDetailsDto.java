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

    public static UsersSubjectGradesDetailsDto from(String subjectName, String firstName, String lastName, double subjectAverage, List<GradeDto> grades) {
        return UsersSubjectGradesDetailsDto.builder()
                .subjectName(subjectName)
                .firstName(firstName)
                .lastName(lastName)
                .subjectAverage(subjectAverage)
                .grades(grades)
                .build();
    }
}
