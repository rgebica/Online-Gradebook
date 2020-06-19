package com.index.model;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="Grade", schema = "gradebook")
public class Grade {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long gradeId;
    private Integer grade;
    private Instant date;
    private String comment;
    private Long userId;
    private Long subjectId;

    public static Grade createGrade(AddGradeDto addGrade) {
        return Grade.builder()
                .subjectId(addGrade.getSubjectId())
                .userId(addGrade.getUserId())
                .date(Instant.now())
                .build();
    }

    public GradeDto dto() {
        return GradeDto.builder()
                .gradeId(gradeId)
                .subjectId(subjectId)
                .userId(userId)
                .grade(grade)
                .date(date)
                .comment(comment)
                .build();
    }
}
