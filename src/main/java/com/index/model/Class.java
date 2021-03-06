package com.index.model;

import com.index.dto.ClassDto;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long classId;
    private String className;

    public ClassDto dto() {
        return ClassDto.builder()
                .classId(classId)
                .className(className)
                .build();
    }
}