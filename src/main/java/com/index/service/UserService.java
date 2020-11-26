package com.index.service;

import com.index.dto.CreateUserDto;
import com.index.dto.UserDto;
import com.index.model.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    @Transactional
    void deleteUsersByIds(String movieIds);

    UserDto getById(long userId);

    User findById(long userId);

    List<UserDto> findUsersByClass(long classId);

}
