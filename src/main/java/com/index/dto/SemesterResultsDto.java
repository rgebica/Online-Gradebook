package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SemesterResultsDto {
    private long subjectId;
    private long userId;
    String subjectName;
    String firstName;
    String lastName;
    String semester;
    double semesterAverage;
    List<SemesterGradeDto> semesterGrades;

    public static SemesterResultsDto from(long subjectId, long userId, String subjectName, UserDto user, String semester, double semesterAverage,
                                          List<SemesterGradeDto> semesterGrades) {
        return SemesterResultsDto.builder()
                .subjectId(subjectId)
                .userId(userId)
                .subjectName(subjectName)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .semester(semester)
                .semesterAverage(semesterAverage)
                .semesterGrades(semesterGrades)
                .build();
    }
}
