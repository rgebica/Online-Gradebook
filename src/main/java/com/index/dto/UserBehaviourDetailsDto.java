package com.index.dto;

import io.swagger.models.auth.In;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserBehaviourDetailsDto {

    long userId;
    String firstName;
    String lastName;
    private List<BehaviourDto> behaviours;

    public static UserBehaviourDetailsDto from(UserDto user, List<BehaviourDto> behaviours) {
        return UserBehaviourDetailsDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .behaviours(behaviours)
                .build();
    }
}
