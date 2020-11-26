package com.index.model;

import com.index.dto.ChildrenDto;
import com.index.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User", schema = "gradebook")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
    private String childrenIds;

//    @ManyToMany(mappedBy = "pupils", fetch = LAZY)
//    private List<Subject> subjects;
//    @ManyToOne
//    private User parent;
//
//    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
//    private List<User> children;

    public UserDto dto() {
        return UserDto.builder()
                .userId(userId)
                .classId(classId.getClassId())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .role(role)
                .childrenIds(childrenIds)
                .build();
    }

    public ChildrenDto childrenDto() {
        return ChildrenDto.builder()
                .userId(userId)
                .className(classId.getClassName())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();
    }
}
