package com.index.model;

import com.index.dto.GradeDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "semester_grade")
public class SemesterGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gradeId;
    private double subjectAverage;
    private Integer finalGrade;
    private String semester;
    private long userId;
    private long subjectId;

}
