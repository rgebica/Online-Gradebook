package com.index.dto;

import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentChildrenDto {
    private String firstName;
    private String lastName;
    private String email;
    private String className;
    private Role role;
    private List<UserDto> children;

    public static ParentChildrenDto from(UserDto userDto, List<UserDto> children) {
        return ParentChildrenDto.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .role(userDto.getRole())
                .children(children)
                .build();
    }
}
