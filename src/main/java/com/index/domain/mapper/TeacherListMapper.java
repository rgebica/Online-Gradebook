package com.index.domain.mapper;
import com.index.domain.converter.Converter;
import com.index.domain.dto.TeacherDto;
import com.index.domain.entity.Teacher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherListMapper implements Converter<List<Teacher>, List<TeacherDto>> {
    @Override
    public List<TeacherDto> convert(List<Teacher> from) {

        return from.stream()
                .map(teacher -> {
                    TeacherDto teacherDto = new TeacherDto();

                    teacherDto.setTeacherId(teacher.getTeacherId());
                    teacherDto.setFirstName(teacher.getFirstName());
                    teacherDto.setLastName(teacher.getLastName());
                    teacherDto.setEmail(teacher.getEmail());
                    teacherDto.setSubject(teacher.getSubject());

                    return teacherDto;
                })
                .collect(Collectors.toList());
    }
}
