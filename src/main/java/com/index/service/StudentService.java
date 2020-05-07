package com.index.service;

import com.index.domain.dto.CreateStudentDto;
import com.index.domain.dto.StudentDto;
import com.index.domain.dto.TeacherDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> findAll();

    void createUser(CreateStudentDto createMovieDto);

}
