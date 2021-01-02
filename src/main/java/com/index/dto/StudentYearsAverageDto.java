package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentYearsAverageDto {
    private long subjectId;
    String subjectName;
    String firstName;
    String lastName;
    private double semesterAverage;
    private double currentAverage;
    private double yearsAverage;

    public static StudentYearsAverageDto from(long subjectId, String subjectName, String firstName, String lastName, double semesterAverage, double currentAverage, double yearsAverage) {
        return StudentYearsAverageDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .firstName(firstName)
                .lastName(lastName)
                .semesterAverage(semesterAverage)
                .currentAverage(currentAverage)
                .yearsAverage(yearsAverage)
                .build();
    }
}
