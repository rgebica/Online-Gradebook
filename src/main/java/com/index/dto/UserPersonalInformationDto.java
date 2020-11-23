package com.index.dto;

import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPersonalInformationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String className;
    private Role role;
//    private long finalAverage;
//    private String finalBehaviour;
//    private long finalPresence;

    public static UserPersonalInformationDto from(ClassDto classDto, UserDto userDto) {
        return UserPersonalInformationDto.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .className(classDto.getClassName())
                .build();
    }
}
