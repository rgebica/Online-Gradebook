package com.index.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.index.dto.*;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "usersubject",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "subject_id") }
    )
    List<Subject> subjects = new ArrayList<>();

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "usersubjectgrade",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "grade_id"))
    @MapKeyJoinColumn(name = "subject_id")
    private List<Grade> grades = new ArrayList<>();

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

    public UserSubjectDto userSubjectDto() {
        return UserSubjectDto.builder()
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .grades(grades)
                .build();
    }

//    UserSubjectsDetailsDto toUserSubjectsDetailsDto() {
//        return UserSubjectsDetailsDto.builder()
//                .firstName(firstName)
//                .lastName(lastName)
//                .userId(userId)
//                .subjects(mapSubjects(subjects))
//                .build();
//    }
//
//    private List<SubjectDto> mapSubjects(List<Subject> subjects) {
//        return subjects.stream()
//                .map(this::mapSubject)
//                .collect(Collectors.toList());
//    }
//
//    private SubjectDto mapSubject(Subject s) {
//        return SubjectDto.builder()
//                .subjectId(s.getSubjectId())
//                .build();
//    }

}
