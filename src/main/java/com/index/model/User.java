package com.index.model;

import com.index.dto.UserDto;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User", schema = "gradebook")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
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
    @Type(type = "list-array")
    @Column(
            name = "children_ids",
            columnDefinition = "bigint[]"
    )
    private List<Long> childrenIds;
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
                .build();
    }
}
