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
    private Long userId;

    public static Behaviour createBehaviour(AddBehaviourDto addBehaviour) {
        return Behaviour.builder()
                .userId(addBehaviour.getUserId())
                .grade(addBehaviour.getGrade())
                .description(addBehaviour.getDescription())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(Instant.now()))
                .build();
    }

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
