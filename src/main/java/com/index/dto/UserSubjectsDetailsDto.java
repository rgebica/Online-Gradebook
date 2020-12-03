package com.index.dto;

import com.index.model.Subject;
import com.index.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserSubjectsDetailsDto {

    long userId;
    String firstName;
    String lastName;
    List<SubjectDto> subjects;

    public static UserSubjectsDetailsDto from(long userId, String firstName, String lastName, List<SubjectDto> subjects) {
        return UserSubjectsDetailsDto.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .subjects(subjects)
                .build();
    }
}