package com.index.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.index.dto.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subjects", schema = "gradebook")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subjectId")
    private Long subjectId;
    @Column(name = "name")
    private String subjectName;
    @ManyToMany(mappedBy = "subjects")
    @JsonIgnore
    private List<User> users = new ArrayList<>();
//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
//    @JoinTable(
//            name = "pupils_subjects",
//            joinColumns = @JoinColumn(
//                    name = "subject_id", referencedColumnName = "subjectId"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "pupil_id", referencedColumnName = "userId"))
//    private List<User> pupils;
//    @ManyToOne
//    private User teacher;

    public SubjectDto dto() {
        return SubjectDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .build();
    }
}
