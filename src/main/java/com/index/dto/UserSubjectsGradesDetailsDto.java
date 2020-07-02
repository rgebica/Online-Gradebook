package com.index.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.OptionalDouble;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubjectsGradesDetailsDto {

    long subjectId;
    long userId;
    String subjectName;
    String firstName;
    String lastName;
    double average;
    List<GradeDto> grades;

    public static UserSubjectsGradesDetailsDto from(long subjectId, String subjectName, double average, UserDto user, List<GradeDto> grades) {
        return UserSubjectsGradesDetailsDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .average(average)
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .grades(grades)
                .build();
    }
}
