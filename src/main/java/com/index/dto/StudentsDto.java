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
public class StudentsDto {
    List<UserDto> students;

    public static StudentsDto from(List<UserDto> students) {
        return StudentsDto.builder()
                .students(students)
                .build();
    }
}
