package com.index.service;

import com.index.dto.AddUsersToClassDto;
import com.index.dto.CreateUserDto;
import com.index.dto.StudentDto;
import com.index.dto.UserDto;
import com.index.model.User;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    void deleteUsersByIds(String movieIds);

    UserDto getById(long userId);

    User findById(long userId);

    List<UserDto> findUsersByClass(long classId);

    StudentDto getAllStudents();

}
