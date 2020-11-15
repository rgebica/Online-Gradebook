package com.index.model;

import com.index.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User", schema = "gradebook")
public class User {
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
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String firstName;
    private String lastName;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "classId")
    private Class classId;
    @ManyToMany(mappedBy = "pupils", fetch = LAZY)
    private List<Subject> subjects;

    public UserDto dto() {
        return UserDto.builder()
                .userId(userId)
                .classId(classId.getClassId())
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .build();
    }
}
