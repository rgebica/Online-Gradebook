package com.index.service.serviceImpl;

import com.index.domain.converter.Converter;
import com.index.domain.dto.CreateUserDto;
import com.index.domain.dto.UserDto;
import com.index.domain.entity.User;
import com.index.domain.repository.UserRepository;
import com.index.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Converter<CreateUserDto, User> userConverter;
    private final Converter<List<User>, List<UserDto>> userListConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Converter<CreateUserDto, User> userConverter,
                           Converter<List<User>,List<UserDto>> userListConverter) {

        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.userListConverter = userListConverter;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userListConverter.convert(users);
    }

    @Override
    public void createUser(CreateUserDto createUserDto) {
        User user = userConverter.convert(createUserDto);
        userRepository.save(user);

    }


}
