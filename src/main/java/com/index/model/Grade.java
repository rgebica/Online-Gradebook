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
@Builder
@Entity
@Table(name = "Grade", schema = "gradebook")
public class Grade {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long gradeId;
    private Integer grade;
    private Integer gradeWeight;
    private String date;
    private String comment;
    @ManyToOne
    private User user;
    private Long subjectId;
    private String addedBy;

    public static Grade createGrade(AddGradeDto addGrade, User user) {
        return Grade.builder()
                .subjectId(addGrade.getSubjectId())
                .user(user)
                .grade(addGrade.getGrade())
                .gradeWeight(addGrade.getGradeWeight())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now()))
                .addedBy(addGrade.getAddedBy())
                .build();
    }

    public GradeDto dto() {
        return GradeDto.builder()
                .gradeId(gradeId)
                .subjectId(subjectId)
                .userId(user.getUserId())
                .grade(grade)
                .gradeWeight(gradeWeight)
                .date(date)
                .comment(comment)
                .addedBy(addedBy)
                .build();
    }
}
