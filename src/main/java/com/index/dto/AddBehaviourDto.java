package com.index.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddBehaviourDto {
    long userId;
    Integer grade;
    String description;
}
