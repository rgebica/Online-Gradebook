package com.index.model;

import com.index.dto.AddPresenceDto;
import com.index.dto.PresenceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Presence", schema = "gradebook")
public class Presence {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    long presenceId;
    long userId;
    long subjectId;
    boolean presence;
    String date;

    public static Presence createPresence(AddPresenceDto addPresenceDto) {
        return Presence.builder()
                .userId(addPresenceDto.getUserId())
                .subjectId(addPresenceDto.getSubjectId())
                .presence(addPresenceDto.isPresence())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now()))
                .build();
    }

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
