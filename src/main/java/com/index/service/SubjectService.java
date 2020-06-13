package com.index.service;

import com.index.dto.AddGradeDto;
import com.index.dto.GradeDto;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Grade;
import com.index.model.Subject;
import com.index.repository.SubjectRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
        return gradeService.addGrade(addGrade);
    }

    List<GradeDetailsDto> getGrades(long userId) {
        Map<Long, List<GradeDto>> gradesBySubject = gradeService.getGradesByUser(userId).stream()
                .collect(Collectors.groupingBy(GradeDto::getSubjectId));
        Set<Long> subjectIds = gradesBySubject.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        checkIfUserExists(userId);
        return subjects.stream()
                .map(subject -> {
                    List<GradeDto> subjectGrades = gradesBySubject.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return subjectGrades.stream()
                            .map(grade -> GradeDetailsDto.builder()
                                    .subjectId(subject.getSubjectId())
                                    .gradeId(grade.getGradeId())
                                    .build())
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    void checkIfSubjectExists(long subjectId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new SpringGradebookException("No subject"));
    }

    void checkIfUserExists(long userId) {
        subjectRepository.findById(userId).orElseThrow(() -> new SpringGradebookException("No user"));
    }

}
