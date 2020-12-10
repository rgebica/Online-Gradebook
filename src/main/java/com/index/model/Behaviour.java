package com.index.model;

import com.index.dto.AddBehaviourDto;
import com.index.dto.BehaviourDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "behaviour")
public class Behaviour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
