package com.index.model;

import com.index.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

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
    private Long classId;
    @ManyToMany(mappedBy = "pupils", fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @ManyToOne
    private User parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<User> children;

    public UserDto dto() {
        return UserDto.builder()
                .userId(userId)
                .classId(classId)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .build();
    }
}
