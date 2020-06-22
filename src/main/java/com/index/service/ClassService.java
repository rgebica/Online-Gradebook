package com.index.service;

import com.index.dto.*;
import com.index.model.Class;
import com.index.repository.ClassRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassService {

    ClassRepository classRepository;
    AuthService userService;

    public ClassService(ClassRepository classRepository, AuthService userService) {
        this.classRepository = classRepository;
        this.userService = userService;
    }

    public List<ClassUsersDetailsDto> getUsersByClassId(long classId) {
        Map<Long, List<UserDto>> usersByClasses = userService.findUsersByClass(classId).stream()
                .collect(Collectors.groupingBy(UserDto::getClassId));
        List<Class> classes = classRepository.findAllById(usersByClasses.keySet());

        return classes.stream()
                .map(c -> {
                    List<UserDto> users = usersByClasses.getOrDefault(c.getClassId(), Collections.emptyList());
                    return ClassUsersDetailsDto.from(c.getClassId(), c.getClassName(), users);
                }).collect(Collectors.toList());
    }
}