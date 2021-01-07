package com.index.service;

import com.index.dto.*;
import com.index.exception.UserNotFoundException;
import com.index.model.*;
import com.index.repository.GradeRepository;
import com.index.repository.SemesterGradeRepository;
import com.index.repository.SubjectRepository;
import com.index.repository.UserRepository;
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
    GradeService gradeService;
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
                    List<GradeDto> grades = gradeRepository.findAllByUserIdAndSubjectId(user.getUserId(), subject.getSubjectId()).stream()
                            .map(Grade::dto)
                            .collect(Collectors.toList());

                    List<SemesterGradeDto> semesterGrades = semesterGradeRepository.findAllByUserIdAndSubjectId(user.getUserId(), subject.getSubjectId()).stream()
                            .map(SemesterGrade::dto)
                            .collect(Collectors.toList());

                    double semesterSubjectAverage = semesterGrades.stream()
                            .filter(semesterGradeDto -> semesterGradeDto.getSemester().equals("Semestr 1"))
                            .mapToDouble(SemesterGradeDto::getSubjectAverage)
                            .sum();

                    int semesterGrade = semesterGrades.stream()
                            .filter(semesterGradeDto -> semesterGradeDto.getSemester().equals("Semestr 1"))
                            .mapToInt(SemesterGradeDto::getFinalGrade)
                            .sum();

                    double yearAverage = Math.round(((semesterSubjectAverage + getGradesAverageBySubject(grades)) / 2) * 100.0) / 100.0;

                    int finalGrade = semesterGrades.stream()
                            .filter(semesterGradeDto -> semesterGradeDto.getSemester().equals("Semestr 2"))
                            .mapToInt(SemesterGradeDto::getFinalGrade)
                            .sum();

                    return UsersSubjectGradesDetailsDto.builder()
                            .subjectName(subject.getSubjectName())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .subjectAverage(getGradesAverageBySubject(grades))
                            .semesterSubjectAverage(semesterSubjectAverage)
                            .yearAverage(yearAverage)
                            .semesterGrade(semesterGrade)
                            .finalGrade(finalGrade)
                            .grades(grades)
                            .build();
                }).collect(Collectors.toList());
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

    public void addSemesterGrade(AddFinalGradeDto addFinalGradeDto) {
        SemesterGrade semesterGrade = new SemesterGrade();
        List<GradeDto> grades = gradeService.getGradesByUser(addFinalGradeDto.getUserId());
//        checkIfSubjectExists(addFinalGradeDto.getSubjectId());
//        checkHasAddAccess();
//        checkAddGradeToStudent(addGrade.getUserId());
//        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
        semesterGrade.setUserId(addFinalGradeDto.getUserId());
        semesterGrade.setSubjectId(addFinalGradeDto.getSubjectId());
        semesterGrade.setFinalGrade(addFinalGradeDto.getFinalGrade());
        semesterGrade.setSemester(addFinalGradeDto.getSemester());
        semesterGrade.setSubjectAverage(getChosenSubjectAverage(addFinalGradeDto.getUserId(), addFinalGradeDto.getSubjectId()));

        semesterGradeRepository.save(semesterGrade);
    }

     double getGradesAverageBySubject(List<GradeDto> grades) {
        double averageRoundedOff = getGradesSumWithWeights(grades) / getWeightsSum(grades);
        return Math.round(averageRoundedOff * 100.0) / 100.0;
    }

     double getGradesSumWithWeights(List<GradeDto> grades) {
        return grades.stream()
                .mapToDouble(grade -> (grade.getGrade() * grade.getGradeWeight()))
                .sum();
    }

     double getWeightsSum(List<GradeDto> grades) {
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

    private double getChosenSubjectAverage(long userId, long subjectId) {
        User user = userService.findById(userId);
        List<UserSubjectsGradesDetailsDto> grades = getUserSubjectsWithGrades(user.getUserId());
        return grades.stream()
                .filter(userSubjectsGradesDetailsDto -> userSubjectsGradesDetailsDto.getSubjectId() == subjectId)
                .mapToDouble(UserSubjectsGradesDetailsDto::getSubjectAverage)
                .sum();
    }
    public List<SemesterResultsDto> getSemesterResults(long userId, String semester) {
        Map<Long, List<SemesterGradeDto>> gradesBySubjectIds = gradeService.getSemesterGrades(userId).stream()
                .collect(Collectors.groupingBy(SemesterGradeDto::getSubjectId));
        Set<Long> subjectIds = gradesBySubjectIds.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<SemesterGradeDto> grades = gradesBySubjectIds.getOrDefault(subject.getSubjectId(), Collections.emptyList()).stream()
                            .filter(semesterGradeDto -> semesterGradeDto.getSemester().equals(semester))
                            .collect(Collectors.toList());
                    double semesterAverage = grades.stream()
                            .mapToDouble(SemesterGradeDto::getFinalGrade)
                            .average()
                            .orElse(Double.NaN);
                    double convertedSemesterAverage = Math.round(semesterAverage * 100.0) / 100.0;
                    return SemesterResultsDto.from(subject.getSubjectId(), userId, subject.getSubjectName(), user, semester,
                            convertedSemesterAverage, grades);
                }).collect(Collectors.toList());
    }
}
