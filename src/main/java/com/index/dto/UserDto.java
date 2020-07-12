package com.index.dto;

import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private Long classId;
    private String firstName;
    private String lastName;
    private Role role;
    private String parentCode;
}
