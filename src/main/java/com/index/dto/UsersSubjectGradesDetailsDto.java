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
    List<UserSubjectDto> users;
//    List<Grade> grades;

    public static UsersSubjectGradesDetailsDto from(String subjectName, List<UserSubjectDto> users) {
        return UsersSubjectGradesDetailsDto.builder()
                .subjectName(subjectName)
                .users(users)
//                .grades(grades)
                .build();
    }
}
