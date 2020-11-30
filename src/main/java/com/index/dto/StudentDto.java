package com.index.dto;

import com.index.model.Role;
import com.index.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    List<UserDto> students;

    public static StudentDto from(List<UserDto> students) {
        return StudentDto.builder()
                .students(students)
                .build();
    }
}
