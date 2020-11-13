package com.index.dto;

import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    public Role role;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
}
