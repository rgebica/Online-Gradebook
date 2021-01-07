package com.index.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddFinalGradeDto {
    private Integer finalGrade;
    private String semester;
    private long userId;
    private long subjectId;
}
