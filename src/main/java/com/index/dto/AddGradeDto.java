package com.index.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddGradeDto {
    Long subjectId;
    Long userId;
    Integer grade;
    String description;
}
