package com.index.dto;

import com.index.model.Role;
import com.index.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserPersonalInformationDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String className;
    private Role role;
//    private long finalAverage;
//    private String finalBehaviour;
//    private long finalPresence;

    public static UserPersonalInformationDto from(ClassDto classDto, User userDto) {
        return UserPersonalInformationDto.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .className(classDto.getClassName())
                .build();
    }
}
