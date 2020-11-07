package com.index.service;

import com.index.dto.ClassUsersDetailsDto;

import java.util.List;

public interface ClassService {
    List<ClassUsersDetailsDto> getUsersByClassId(long classId);
}
