package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.*;
import com.index.repository.*;
import com.index.service.AuthService;
import com.index.service.DateService;
import com.index.service.GradeService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class GradeServiceImpl implements GradeService {

    GradeRepository gradeRepository;
    SubjectRepository subjectRepository;
    PresenceRepository presenceRepository;
    BehaviourRepository behaviourRepository;
    AuthService authService;
    UserService userService;
    SemesterGradeRepository semesterGradeRepository;

    @Override
    public void addGrade(AddGradeDto addGrade) {
        Grade grade = new Grade();
        checkIfSubjectExists(addGrade.getSubjectId());
//        checkHasAddAccess();
//        checkAddGradeToStudent(addGrade.getUserId());
//        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
       User user = userService.findById(addGrade.getUserId());
        grade.setUserId(addGrade.getUserId());
        grade.setSubjectId(addGrade.getSubjectId());
        grade.setGrade(addGrade.getGrade());
        grade.setGradeWeight(addGrade.getGradeWeight());
        grade.setComment(addGrade.getComment());
        grade.setAddedBy(addedBy());
        grade.setDate(DateService.getFormattedDate());

        user.setGrades(Collections.singletonList(grade));
        gradeRepository.save(grade);
    }

    @Override
    public List<GradeDto> getGradesByUser(long userId) {
        return gradeRepository.findAllByUserId(userId).stream()
                .map(Grade::dto)
                .collect(Collectors.toList());
    }

//    public List<SubjectDto> getSubjectsByUser(long userId) {
//        return subjectRepository.findAllByUserId(userId).stream()
//                .map(Subject::dto)
//                .collect(Collectors.toList());
//    }

    public List<PresenceDto> getPresenceByUserId(long userId) {
        return presenceRepository.findAllByUserId(userId).stream()
                .map(Presence::dto)
                .collect(Collectors.toList());
    }

    public List<BehaviourDto> getBehaviourByUser(long userId) {
        return behaviourRepository.findAllByUserId(userId).stream()
                .map(Behaviour::dto)
                .collect(Collectors.toList());
    }

    void checkIfSubjectExists(long subjectId) {
        subjectRepository.findById(subjectId).orElseThrow(() -> new SpringGradebookException("No subject"));
    }

    void checkHasAddAccess() {
        User user = authService.getCurrentUser();
        if (!user.getRole().equals(Role.ROLE_TEACHER)) {
            throw new SpringGradebookException("Has no add access");
        }
    }

    void checkAddGradeToStudent(long userId) {
        User user = userService.findById(userId);
        if (!user.getRole().equals(Role.ROLE_STUDENT)) {
            throw new SpringGradebookException("Bad userId");
        }
    }

    long addedBy() {
        return authService.getCurrentUser().getUserId();
    }

    @Override
    @Transactional
    public void deleteGradesByIds(String gradeIds) {
        String[] splitedIds = gradeIds.split(",");
        List<Long> parsedIds = Arrays.stream(splitedIds)
                .map(Long::parseLong)
                .collect(Collectors.toList());
        gradeRepository.deleteGradesByIds(parsedIds);
    }

    @Override
    public void editGrade(EditGradeDto editGradeDto, long gradeId) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> new SpringGradebookException("Grade does not exist"));
        grade.setSubjectId(editGradeDto.getSubjectId());
        grade.setGrade(editGradeDto.getGrade());
        grade.setComment(editGradeDto.getComment());
        grade.setUserId(editGradeDto.getUserId());
        gradeRepository.save(grade);
    }

    @Override
    public void addSemesterGrade(AddFinalGradeDto addFinalGradeDto) {
        SemesterGrade semesterGrade = new SemesterGrade();
        checkIfSubjectExists(addFinalGradeDto.getSubjectId());
//        checkHasAddAccess();
//        checkAddGradeToStudent(addGrade.getUserId());
//        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
        semesterGrade.setUserId(addFinalGradeDto.getUserId());
        semesterGrade.setSubjectId(addFinalGradeDto.getSubjectId());
        semesterGrade.setFinalGrade(addFinalGradeDto.getFinalGrade());
        semesterGrade.setSemester(addFinalGradeDto.getSemester());
        semesterGrade.setSubjectAverage(addFinalGradeDto.getSubjectAverage());

        semesterGradeRepository.save(semesterGrade);
    }
}