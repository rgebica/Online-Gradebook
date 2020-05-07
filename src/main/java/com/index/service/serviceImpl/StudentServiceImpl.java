package com.index.service.serviceImpl;

import com.index.domain.converter.Converter;
import com.index.domain.dto.CreateStudentDto;
import com.index.domain.dto.StudentDto;
import com.index.domain.dto.TeacherDto;
import com.index.domain.entity.Teacher;
import com.index.domain.entity.User;
import com.index.domain.repository.StudentRepository;
import com.index.domain.repository.TeacherRepository;
import com.index.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final Converter<CreateStudentDto, User> userConverter;
    private final Converter<List<User>, List<StudentDto>> userListConverter;
    private final TeacherRepository teacherRepository;
    private final Converter<List<Teacher>, List<TeacherDto>> teacherListConverter;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              Converter<CreateStudentDto, User> userConverter,
                              Converter<List<User>, List<StudentDto>> userListConverter,
                              TeacherRepository teacherRepository,
                              Converter<List<Teacher>, List<TeacherDto>> teacherListConverter) {

        this.studentRepository = studentRepository;
        this.userConverter = userConverter;
        this.userListConverter = userListConverter;
        this.teacherRepository = teacherRepository;
        this.teacherListConverter = teacherListConverter;
    }

    @Override
    public List<StudentDto> findAll() {
        List<User> users = studentRepository.findAll();
        return userListConverter.convert(users);
    }

    @Override
    public void createUser(CreateStudentDto createStudentDto) {
        User user = userConverter.convert(createStudentDto);
        studentRepository.save(user);

    }
}
