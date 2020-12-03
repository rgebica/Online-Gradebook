package com.index.model;

import com.index.dto.AddBehaviourDto;
import com.index.dto.BehaviourDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Table(name = "Behaviour", schema = "gradebook")
public class Behaviour {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long behaviourId;
    private Integer grade;
    private String date;
    private String description;
    private long userId;
    private long addedBy;


    public BehaviourDto dto() {
        return BehaviourDto.builder()
                .behaviourId(behaviourId)
                .userId(userId)
                .grade(grade)
                .date(date)
                .description(description)
                .build();
    }
}
