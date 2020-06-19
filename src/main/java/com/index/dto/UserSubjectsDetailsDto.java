package com.index.dto;


import com.index.dto.GradeDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubjectsDetailsDto {

    long subjectId;
    long userId;
    String subjectName;
    String firstName;
    String lastName;
    List<GradeDto> grades;

    public static UserSubjectsDetailsDto from(long subjectId, String subjectName, UserDto user, List<GradeDto> grades) {
        return UserSubjectsDetailsDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .grades(grades)
                .build();
    }
}
