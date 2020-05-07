package com.index.service;

import com.index.domain.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> findAll();
}
