package com.index.service;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Grade;
import com.index.model.Role;
import com.index.model.Subject;
import com.index.model.User;
import com.index.repository.SubjectRepository;
import com.index.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {

    SubjectRepository subjectRepository;
    GradeService gradeService;
    UserService userService;
    UserRepository userRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, GradeService gradeService, UserService userService, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    GradeDto addGrade(AddGradeDto addGrade) {
        checkIfSubjectExists(addGrade.getSubjectId());
        checkHasAddAccess(addGrade.getUserId());
        return gradeService.addGrade(addGrade);
    }

    private void checkHasAddAccess(long userId) {
        UserDto user = userService.getById(userId);
        if (!user.getRole().equals(Role.TEACHER)) {
            throw new SpringGradebookException("Has no add access");
        }
    }

    public List<UserSubjectsGradesDetailsDto> getUserSubjectsWithGrades(long userId) {
        Map<Long, List<GradeDto>> gradesBySubjectIds = gradeService.getGradesByUser(userId).stream()
                .collect(Collectors.groupingBy(GradeDto::getSubjectId));
        Set<Long> subjectIds = gradesBySubjectIds.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<GradeDto> grades = gradesBySubjectIds.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return UserSubjectsGradesDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), user, grades);
                }).collect(Collectors.toList());
    }

    void checkIfSubjectExists(long subjectId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new SpringGradebookException("No subject"));
    }

    public UserSubjectsDetailsDto getSubjectsByUserId(long userId) {
        UserDto user = userService.getById(userId);
        List<Long> subjectIds = gradeService.getSubjectsByUser(userId).stream()
                .map(SubjectDto::getSubjectId)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        List<SubjectDto> subjects = subjectRepository.findAllById(subjectIds).stream()
                .map(Subject::dto)
                .collect(Collectors.toList());

        return UserSubjectsDetailsDto.builder()
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .subjects(subjects)
                .build();
    }
}
