package com.index.service;

import com.index.dto.AddClassDto;
import com.index.dto.AddUsersToClassDto;
import com.index.dto.ClassDto;
import com.index.dto.ClassUsersDetailsDto;
import com.index.model.Class;

import java.util.List;

public interface ClassService {
    List<ClassUsersDetailsDto> getUsersByClassId(long classId);

    void addUserToClass(AddUsersToClassDto addUsersToClassDto);

    void addClass(AddClassDto addClassDto);

    List<ClassDto> getAllClasses();

    ClassDto findClassByClassId(long classId);
}

