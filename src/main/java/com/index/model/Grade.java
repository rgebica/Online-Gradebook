package com.index.model;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Grade", schema = "gradebook")
public class Grade {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long gradeId;
    private Integer grade;
    private Integer gradeWeight;
    private String date;
    private String comment;
    private Long userId;
    private Long subjectId;

    public static Grade createGrade(AddGradeDto addGrade) {
        return Grade.builder()
                .subjectId(addGrade.getSubjectId())
                .userId(addGrade.getUserId())
                .grade(addGrade.getGrade())
                .gradeWeight(addGrade.getGradeWeight())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now()))
                .build();
    }

    public GradeDto dto() {
        return GradeDto.builder()
                .gradeId(gradeId)
                .subjectId(subjectId)
                .userId(userId)
                .grade(grade)
                .gradeWeight(gradeWeight)
                .date(date)
                .comment(comment)
                .build();
    }
}
