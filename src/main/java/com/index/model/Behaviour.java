package com.index.model;

import com.index.dto.AddBehaviourDto;
import com.index.dto.BehaviourDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "behaviour")
public class Behaviour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long behaviourId;
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
