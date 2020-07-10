package com.index.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPresenceDetailsDto {

    long userId;
    String firstName;
    String lastName;
    long subjectId;
    String subjectName;
    long presenceCounter;
    long absenceCounter;
    long presencePercentage;

    public static UserPresenceDetailsDto from(long subjectId, String subjectName, UserDto user, long presenceCounter, long absenceCounter, long presencePercentage) {
        return UserPresenceDetailsDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .subjectId(subjectId)
                .subjectName(subjectName)
                .presenceCounter(presenceCounter)
                .absenceCounter(absenceCounter)
                .presencePercentage(presencePercentage)
                .build();
    }
}

