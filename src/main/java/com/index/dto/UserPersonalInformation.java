package com.index.dto;

import com.index.model.Role;
import com.index.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPersonalInformation {
    private String firstName;
    private String lastName;
    private String email;
    private String className;
    private Role role;
//    private long finalAverage;
//    private String finalBehaviour;
//    private long finalPresence;

    public static UserPersonalInformation from(ClassDto classDto, UserDto userDto) {
        return UserPersonalInformation.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .className(classDto.getClassName())
                .build();
    }
}
