package com.index.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "teacher", schema = "index")
public class Teacher {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "teacherId")
        private Long teacherId;

        @Column(name = "firstname")
        private String firstName;

        @Column(name = "lastname")
        private String lastName;

        @Column(name = "email")
        private String email;

        @Column(name = "subject")
        private String subject;

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Teacher() {
    }
}
