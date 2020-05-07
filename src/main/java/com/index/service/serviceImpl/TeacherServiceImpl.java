package com.index.service.serviceImpl;

import com.index.domain.converter.Converter;
import com.index.domain.dto.TeacherDto;
import com.index.domain.entity.Teacher;
import com.index.domain.repository.TeacherRepository;
import com.index.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final Converter<List<Teacher>, List<TeacherDto>> teacherListConverter;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository,
                             Converter<List<Teacher>,List<TeacherDto>> teacherListConverter) {

        this.teacherRepository = teacherRepository;
        this.teacherListConverter = teacherListConverter;
    }

    @Override
    public List<TeacherDto> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teacherListConverter.convert(teachers);
    }
}
