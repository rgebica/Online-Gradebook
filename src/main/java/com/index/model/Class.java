package com.index.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Class", schema = "gradebook")
public class Class {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long class_id;
    private String className;
}