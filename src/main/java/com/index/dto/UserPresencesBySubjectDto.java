package com.index.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserPresencesBySubjectDto {
    String subjectName;
    String firstName;
    String lastName;
    List<PresenceDto> presences;
    long presenceCounter;
    long absenceCounter;
    long presencePercentage;

    public static UserPresencesBySubjectDto from(String subjectName, String firstName, String lastName, List<PresenceDto> presences, long presenceCounter, long absenceCounter, long presencePercentage) {
        return UserPresencesBySubjectDto.builder()
                .subjectName(subjectName)
                .firstName(firstName)
                .lastName(lastName)
                .presences(presences)
                .presenceCounter(presenceCounter)
                .absenceCounter(absenceCounter)
                .presencePercentage(presencePercentage)
                .build();
    }
}
