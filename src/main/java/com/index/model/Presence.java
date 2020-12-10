package com.index.model;

import com.index.dto.AddPresenceDto;
import com.index.dto.PresenceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "presence", schema = "gradebook")
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long presenceId;
    private long userId;
    private long subjectId;
    private boolean presence;
    private String date;
    private long addedBy;

    public PresenceDto dto() {
        return PresenceDto.builder()
                .presenceId(presenceId)
                .subjectId(subjectId)
                .userId(userId)
                .presence(presence)
                .date(date)
                .build();
    }
}
