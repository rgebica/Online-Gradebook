package com.index.service;

import com.index.dto.*;
import com.index.exception.UserNotFoundException;
import com.index.exceptions.SpringGradebookException;
import com.index.model.*;
import com.index.repository.GradeRepository;
import com.index.repository.SemesterGradeRepository;
import com.index.repository.SubjectRepository;
import com.index.repository.UserRepository;
import com.index.service.serviceImpl.GradeServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectService {

    SubjectRepository subjectRepository;
    GradeServiceImpl gradeService;
    UserService userService;
    UserRepository userRepository;
    GradeRepository gradeRepository;
    SemesterGradeRepository semesterGradeRepository;

    public void createSubject(CreateSubjectDto createSubjectDto) {
        Subject subject = new Subject();
        subject.setSubjectName(createSubjectDto.getSubjectName());
        subjectRepository.save(subject);
    }

    public void addUserToSubject(AddUserToSubjectDto addUserToSubjectDto) {
        String[] subjectIds = addUserToSubjectDto.getSubjectIds().split(",");
        List<Long> parsedSubjectIds = Arrays.stream(subjectIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Subject> subjects = subjectRepository.findAllById(parsedSubjectIds);

        User user = userService.findById(addUserToSubjectDto.getUserId());
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

    public List<UsersSubjectGradesDetailsDto> getUsersWithGradesBySubject(long subjectId) {
        Subject subject = findById(subjectId);

        List<UserDto> users = subject.getUsers().stream()
                .map(User::dto)
                .collect(Collectors.toList());

        return users.stream()
                .map(user -> {
                    List<GradeDto> grades = gradeRepository.findAllByUserIdAndSubjectId(user.getUserId(), subject.getSubjectId())
                            .stream()
                            .map(Grade::dto)
                            .collect(Collectors.toList());

                    return UsersSubjectGradesDetailsDto.builder()
                            .subjectName(subject.getSubjectName())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .subjectAverage(getGradesAverageBySubject(grades))
                            .grades(grades)
                            .build();
                }).collect(Collectors.toList());
    }

    private double getGradesAverageBySubject(List<GradeDto> grades) {
        double averageRoundedOff = getGradesSumWithWeights(grades) / getWeightsSum(grades);
        return Math.round(averageRoundedOff * 100.0) / 100.0;
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

    public double getFinalAverage(long userId) {
        User user = userService.findById(userId);
        List<UserSubjectsGradesDetailsDto> grades = getUserSubjectsWithGrades(user.getUserId());

        double finalAverage = grades.stream()
                .mapToDouble(UserSubjectsGradesDetailsDto::getSubjectAverage)
                .average()
                .orElse(Double.NaN);

        return Math.round(finalAverage * 100.0) / 100.0;
    }


    public Subject findById(long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new UserNotFoundException(subjectId));
    }

    public boolean checkIfUserExist(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return true;
    }

    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(Subject::dto)
                .collect(Collectors.toList());
    }

    public List<GradeDto> getGradesByUserId(long userId) {
        return gradeRepository.findAllByUserId(userId).stream()
                .map(Grade::dto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersBySubjectId(long subjectId) {
        Subject subject = findById(subjectId);
        return subject.getUsers().stream()
                .filter(user -> user.getRole().equals(Role.ROLE_STUDENT))
                .map(User::dto)
                .collect(Collectors.toList());
    }

    public List<StudentYearsAverageDto> getUsersWithAveragesBySubject(long subjectId) {
        Subject subject = findById(subjectId);

        List<UserDto> users = subject.getUsers().stream()
                .map(User::dto)
                .collect(Collectors.toList());

        return users.stream()
                .map(user -> {
                    List<GradeDto> grades = gradeRepository.findAllByUserIdAndSubjectId(user.getUserId(), subject.getSubjectId())
                            .stream()
                            .map(Grade::dto)
                            .collect(Collectors.toList());

                    double currentAverage = getGradesAverageBySubject(grades);

                    double lastSubjectAverage = semesterGradeRepository.findByUserIdAndSubjectId(user.getUserId(), subjectId)
                            .map(SemesterGrade::getSubjectAverage)
                            .orElseThrow(() -> new SpringGradebookException("Grade does not exist"));

                    double yearsAverage = ((currentAverage + lastSubjectAverage) / 2);

                    return StudentYearsAverageDto.builder()
                            .subjectName(subject.getSubjectName())
                            .subjectId(subjectId)
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .currentAverage(currentAverage)
                            .semesterAverage(lastSubjectAverage)
                            .yearsAverage(yearsAverage)
                            .build();
                }).collect(Collectors.toList());
    }
}
