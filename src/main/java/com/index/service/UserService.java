package com.index.service;

import com.index.domain.dto.CreateUserDto;
import com.index.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    void createUser(CreateUserDto createMovieDto);

}
