package com.index.model;

import com.index.dto.ClassDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class", schema = "gradebook")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
    private String className;

    public ClassDto dto() {
        return ClassDto.builder()
                .classId(classId)
                .className(className)
                .build();
    }
}