package com.index.domain.dto;

import java.io.Serializable;
import java.sql.Date;

public class CreateUserDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String dateofBirth;

    public CreateUserDto() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDateofBirth() {
        return dateofBirth;
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateofBirth=" + dateofBirth +
                '}';
    }
}
