package com.index.domain.mapper;

import com.index.domain.converter.Converter;
import com.index.domain.dto.CreateStudentDto;
import com.index.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements Converter<CreateStudentDto, User> {

    @Override
    public User convert(CreateStudentDto createStudentDto) {

        User user = new User();
        user.setFirstName(createStudentDto.getFirstName());
        user.setLastName(createStudentDto.getLastName());
        user.setEmail(createStudentDto.getEmail());
        user.setDateofBirth(createStudentDto.getDateofBirth());
        return user;
    }
}
