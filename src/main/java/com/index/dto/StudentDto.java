package com.index.dto;

import com.index.model.Class;
import com.index.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private long userId;
    private Long classId;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Class className;
}
