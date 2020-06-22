package com.index.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassUsersDetailsDto {
    long classId;
    String className;
    List<UserDto> users;

    public static ClassUsersDetailsDto from(long classId, String className, List<UserDto> users) {
        return ClassUsersDetailsDto.builder()
                .classId(classId)
                .className(className)
                .users(users)
                .build();
    }
}
