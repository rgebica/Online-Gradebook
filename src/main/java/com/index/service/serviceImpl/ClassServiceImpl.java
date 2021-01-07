package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exception.ClassNotFoundException;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Class;
import com.index.model.User;
import com.index.repository.ClassRepository;
import com.index.repository.UserRepository;
import com.index.service.ClassService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ClassServiceImpl implements ClassService {

    ClassRepository classRepository;
    UserService userService;
    UserRepository userRepository;

    @Override
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

    @Override
    public void addUserToClass(AddUsersToClassDto addUsersToClassDto) {
        User user = userService.findById(addUsersToClassDto.getUserId());
        user.setClassId(addUsersToClassDto.getClassId());
        userRepository.save(user);
    }

    @Override
    public void addClass(AddClassDto addClassDto) {
        Class _class = new Class();
        _class.setClassName(addClassDto.getClassName());
        classRepository.save(_class);
    }

    @Override
    public List<ClassDto> getAllClasses() {
        return classRepository.findAll().stream()
                .map(Class::dto)
                .collect(Collectors.toList());
    }

    public Class findClassById(long classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new SpringGradebookException("Class not found"));
    }

    @Override
    public ClassDto findClassByClassId(long classId) {
        return classRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundException(classId))
                .dto();
    }
}