package com.index.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gradeId;
    private Integer grade;
    private Integer gradeWeight;
    private String date;
    private String comment;
    private long userId;
//    @ManyToOne
//    private User user;
    private long subjectId;
    private Long addedBy;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public GradeDto dto() {
        return GradeDto.builder()
                .gradeId(gradeId)
                .subjectId(subjectId)
                .userId(userId)
                .grade(grade)
                .gradeWeight(gradeWeight)
                .date(date)
                .comment(comment)
                .addedBy(addedBy)
                .build();
    }
}
