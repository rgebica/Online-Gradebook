package com.index.service;

import com.index.dto.*;
import com.index.model.Subject;
import com.index.repository.SubjectRepository;
import com.index.service.serviceImpl.GradeServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {

    SubjectRepository subjectRepository;
    GradeServiceImpl gradeService;
    UserService userService;

    public List<UserSubjectsGradesDetailsDto> getUserSubjectsWithGrades(long userId) {

        Map<Long, List<GradeDto>> gradesBySubjectIds = gradeService.getGradesByUser(userId).stream()
                .collect(Collectors.groupingBy(GradeDto::getSubjectId));
        Set<Long> subjectIds = gradesBySubjectIds.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<GradeDto> grades = gradesBySubjectIds.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return UserSubjectsGradesDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), getGradesAverageBySubject(grades), user, grades);
                }).collect(Collectors.toList());
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

    private double getGradesAverageBySubject(List<GradeDto> grades) {
        double average = getGradesSumWithWeights(grades) / getWeightsSum(grades);

        return BigDecimal.valueOf(average)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double getGradesSumWithWeights(List<GradeDto> grades) {
        return grades.stream()
                .mapToDouble(grade -> (grade.getGrade() * grade.getGradeWeight()))
                .sum();
    }

    private double getWeightsSum(List<GradeDto> grades) {
        return grades.stream()
                .mapToDouble(GradeDto::getGradeWeight)
                .sum();
    }
}
