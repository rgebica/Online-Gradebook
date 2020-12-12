package com.index.dto;

import com.index.model.Grade;
import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSubjectDto {
    private long userId;
    private String firstName;
    private String lastName;
    private List<Grade> grades;
}
