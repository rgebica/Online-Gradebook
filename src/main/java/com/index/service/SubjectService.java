package com.index.service;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import com.index.dto.UserDto;
import com.index.dto.UserSubjectsDetailsDto;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Role;
import com.index.model.Subject;
import com.index.repository.SubjectRepository;
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
    AuthService userService;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, GradeService gradeService, AuthService userService) {
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.userService = userService;
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

    public List<UserSubjectsDetailsDto> getUserSubjectsWithGrades(long userId) {
        Map<Long, List<GradeDto>> gradesBySubjectIds = gradeService.getGradesByUser(userId).stream()
                .collect(Collectors.groupingBy(GradeDto::getSubjectId));
        Set<Long> subjectIds = gradesBySubjectIds.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<GradeDto> grades = gradesBySubjectIds.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return UserSubjectsDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), user, grades);
                }).collect(Collectors.toList());
    }

    void checkIfSubjectExists(long subjectId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new SpringGradebookException("No subject"));
    }
}
