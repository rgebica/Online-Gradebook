package com.index.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indexNumber;
    private String firstName;
    private String LastName;
}
