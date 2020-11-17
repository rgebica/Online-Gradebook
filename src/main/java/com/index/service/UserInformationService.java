package com.index.service;

import com.index.dto.ClassDto;
import com.index.dto.UserDto;
import com.index.dto.UserPersonalInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserInformationService {

    UserService userService;
    ClassService classService;

    public UserPersonalInformation getUserInformation(long userId) {
        UserDto user = userService.getById(userId);
        ClassDto classDto = classService.findClassByClassId(user.getClassId());

        return UserPersonalInformation.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .className(classDto.getClassName())
                .build();
    }
}
