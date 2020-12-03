package com.index.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddUserToSubjectDto {
    private long userId;
    private long subjectId;
}
