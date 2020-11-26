package com.index.dto;

import com.index.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildrenDto {
    private Long userId;
    private String className;
    private String firstName;
    private String lastName;
    private String email;
}
