package com.index.model;

import com.index.dto.AddPresenceDto;
import com.index.dto.PresenceDto;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "presence")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long presenceId;
    private long userId;
    private long subjectId;
    private boolean presence;
    private String date;
    private long addedBy;
    private String status;

    public PresenceDto dto() {
        return PresenceDto.builder()
                .presenceId(presenceId)
                .subjectId(subjectId)
                .userId(userId)
                .presence(presence)
                .date(date)
                .status(status)
                .build();
    }
}
