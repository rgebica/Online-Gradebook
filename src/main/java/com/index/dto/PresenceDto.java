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
public class PresenceDto {
    private long userId;
    private long presenceId;
    private long subjectId;
    private boolean presence;
    private Instant date;
}
