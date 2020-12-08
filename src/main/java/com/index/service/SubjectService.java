package com.index.service;

import com.index.dto.*;
import com.index.exception.UserNotFoundException;
import com.index.model.Grade;
import com.index.model.Subject;
import com.index.model.User;
import com.index.repository.GradeRepository;
import com.index.repository.SubjectRepository;
import com.index.repository.UserRepository;
import com.index.service.serviceImpl.AuthServiceImpl;
import com.index.service.serviceImpl.GradeServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toCollection;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {

    SubjectRepository subjectRepository;
    GradeServiceImpl gradeService;
    UserService userService;
    UserRepository userRepository;
    GradeRepository gradeRepository;

    public void createSubject(CreateSubjectDto createSubjectDto) {
        Subject subject = new Subject();
        subject.setSubjectName(createSubjectDto.getSubjectName());
        subjectRepository.save(subject);
    }

    public void addUserToSubject(AddUserToSubjectDto addUserToSubjectDto, long userId) {
        String[] subjectIds = addUserToSubjectDto.getSubjectIds().split(",");
        List<Long> parsedSubjectIds = Arrays.stream(subjectIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Subject> subjects = subjectRepository.findAllById(parsedSubjectIds);

        User user = userService.findById(userId);
        user.setSubjects(subjects);
        userRepository.save(user);
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
                    return UserSubjectsGradesDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), getGradesAverageBySubject(grades), user, grades);
                }).collect(Collectors.toList());
    }

    public UserSubjectsDetailsDto getSubjectsByUserId(long userId) {
        User user = userService.findById(userId);
        List<SubjectDto> subjects = user.getSubjects().stream()
                .map(Subject::dto)
                .collect(Collectors.toList());

        return UserSubjectsDetailsDto.builder()
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .subjects(subjects)
                .build();
    }

    public UsersSubjectGradesDetailsDto getUsersWithGradesBySubject(long subjectId) {
        Subject subject = findById(subjectId);

        List<UserSubjectDto> users = subject.getUsers().stream()
                .map(User::userSubjectDto)
                .collect(Collectors.toList());

        return UsersSubjectGradesDetailsDto.builder()
                .subjectName(subject.getSubjectName())
                .users(users)
//                .grades(user.getGrades())
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

    public double getFinalAverage() {
        List<UserSubjectsGradesDetailsDto> gradesDetails = new ArrayList<>();
        double finalAverage = gradesDetails.stream()
                .mapToDouble(UserSubjectsGradesDetailsDto::getSubjectAverage)
                .average()
                .orElse(-100);

        return BigDecimal.valueOf(finalAverage)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public Subject findById(long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new UserNotFoundException(subjectId));
    }

    public boolean checkIfUserExist(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return true;
    }
}
