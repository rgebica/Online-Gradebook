package com.index.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BehaviourDto {
    private long behaviourId;
    private long userId;
    private int grade;
    private String date;
    private String description;
}
