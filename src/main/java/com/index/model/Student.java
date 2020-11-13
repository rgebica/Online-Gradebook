package com.index.model;

import com.index.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User", schema = "gradebook")
public class Student {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_STUDENT;
    private String firstName;
    private String lastName;
    private Long classId;
    private String parentCode;

    public StudentDto dto() {
        return StudentDto.builder()
                .userId(userId)
                .classId(classId)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .parentCode(parentCode)
                .build();
    }
}