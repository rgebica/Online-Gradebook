package com.index.dto;

import com.index.model.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long userId;
    private Long classId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String childrenIds;
    private String className;
}
