package com.index.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubjectsGradesDetailsDto {

    long subjectId;
    long userId;
    String subjectName;
    String firstName;
    String lastName;
    double subjectAverage;
    List<GradeDto> grades;

    public static UserSubjectsGradesDetailsDto from(long subjectId, String subjectName, double subjectAverage, UserDto user, List<GradeDto> grades) {
        return UserSubjectsGradesDetailsDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .subjectAverage(subjectAverage)
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .grades(grades)
                .build();
    }
}
