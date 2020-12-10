package com.index.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddUsersToClassDto {
    private long userId;
    private long classId;
}