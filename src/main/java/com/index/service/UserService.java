package com.index.service;

import com.index.dto.*;
import com.index.model.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    void deleteUsersByIds(String movieIds);

    UserDto getById(long userId);

    User findById(long userId);

    List<UserDto> findUsersByClass(long classId);

    StudentsDto getAllStudents();

    List<UserDto> getAllUsers();

    List<UserDto> getAllTeachers();

    List<UserDto> getAllParents();

    void editPassword(EditPasswordDto editPasswordDto, long userId);

    void editBasicInfo(UserEditInfoDto userEditInfoDto, long userId);

    @Transactional
    void resetPassword(long userId);
}
