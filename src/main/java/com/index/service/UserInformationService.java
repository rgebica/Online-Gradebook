package com.index.service;

import com.index.dto.ClassDto;
import com.index.dto.UserDto;
import com.index.dto.UserPersonalInformationDto;
import com.index.exception.UserNotFoundException;
import com.index.exceptions.SpringGradebookException;
import com.index.model.User;
import com.index.repository.ClassRepository;
import com.index.repository.UserRepository;
import com.index.service.serviceImpl.AuthServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInformationService {

    ClassService classService;
    UserService userService;
    UserRepository userRepository;
    ClassRepository classRepository;

    public UserPersonalInformationDto getUserInformation(long userId) {
        UserDto user = getById(userId);
        ClassDto classDto = getClassById(user.getClassId());

        return UserPersonalInformationDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .className(classDto.getClassName())
                .build();
    }

    public UserDto getById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .dto();
    }

    public ClassDto getClassById(long classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new SpringGradebookException("No class"))
                .dto();
    }
}
