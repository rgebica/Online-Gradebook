package com.index.service;

import com.index.dto.ClassDto;
import com.index.dto.UserPersonalInformationDto;
import com.index.model.User;
import com.index.service.serviceImpl.AuthServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInformationService {

    ClassService classService;
    UserService userService;

    public UserPersonalInformationDto getUserInformation(long userId) {
        User user = userService.findById(userId);
        ClassDto classDto = classService.findClassByClassId(userId);

        return UserPersonalInformationDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .className(classDto.getClassName())
                .build();
    }
}