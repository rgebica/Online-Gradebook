package com.index.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectDto {
    long subjectId;
    String subjectName;
    long userId;
}
