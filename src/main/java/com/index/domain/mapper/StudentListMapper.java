package com.index.domain.mapper;

import com.index.domain.converter.Converter;
import com.index.domain.dto.StudentDto;
import com.index.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentListMapper implements Converter<List<User>, List<StudentDto>> {
    @Override
    public List<StudentDto> convert(List<User> from) {

        return from.stream()
                .map(user -> {
                    StudentDto studentDto = new StudentDto();

                    studentDto.setIndexNumber(user.getIndexNumber());
                    studentDto.setFirstName(user.getFirstName());
                    studentDto.setLastName(user.getLastName());
                    studentDto.setEmail(user.getEmail());
                    studentDto.setDateofBirth(user.getDateofBirth());

                    return studentDto;
                })
                .collect(Collectors.toList());
    }

}
