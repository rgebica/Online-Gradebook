package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildrenDto {
    private long userId;
    private long classId;
    private String firstName;
    private String lastName;
    private String email;
}
