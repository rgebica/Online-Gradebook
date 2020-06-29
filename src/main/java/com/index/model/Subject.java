package com.index.model;

import com.index.dto.SubjectDto;
import com.index.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public SubjectDto dto() {
        return SubjectDto.builder()
                .subjectId(subjectId)
                .subjectName(subjectName)
                .build();
    }
}
