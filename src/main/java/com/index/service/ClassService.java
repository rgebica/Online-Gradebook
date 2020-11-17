package com.index.service;

import com.index.dto.ClassDto;
import com.index.dto.ClassUsersDetailsDto;

import java.util.List;

public interface ClassService {
    List<ClassUsersDetailsDto> getUsersByClassId(long classId);

    ClassDto findClassByClassId(long classId);
}
