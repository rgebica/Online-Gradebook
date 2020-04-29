package com.index.domain.mapper;

import com.index.domain.converter.Converter;
import com.index.domain.dto.CreateUserDto;
import com.index.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Converter<CreateUserDto, User> {

    @Override
    public User convert(CreateUserDto createUserDto) {

        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setDateofBirth(createUserDto.getDateofBirth());
        return user;
    }
}
