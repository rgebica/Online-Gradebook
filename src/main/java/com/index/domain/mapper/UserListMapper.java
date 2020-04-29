package com.index.domain.mapper;

import com.index.domain.converter.Converter;
import com.index.domain.dto.UserDto;
import com.index.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserListMapper implements Converter<List<User>, List<UserDto>> {
    @Override
    public List<UserDto> convert(List<User> from) {

        return from.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();

                    userDto.setIndexNumber(user.getIndexNumber());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setEmail(user.getEmail());
                    userDto.setDateofBirth(user.getDateofBirth());

                    return userDto;
                })
                .collect(Collectors.toList());
    }

}
