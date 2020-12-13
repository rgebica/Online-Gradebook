package com.index.service;

import com.index.dto.*;
import com.index.model.User;

import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    void deleteUsersByIds(String movieIds);

    UserDto getById(long userId);

    User findById(long userId);

    List<UserDto> findUsersByClass(long classId);

    StudentDto getAllStudents();

    void editPassword(EditPasswordDto editPasswordDto, long userId);

    void editBasicInfo(UserEditInfoDto userEditInfoDto, long userId);
}
