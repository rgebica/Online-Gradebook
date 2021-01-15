package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exception.GradeNotFoundException;
import com.index.exception.HasNoAddAccessException;
import com.index.exception.SubjectNotFoundException;
import com.index.exceptions.SpringGradebookException;
import com.index.model.*;
import com.index.repository.*;
import com.index.service.*;
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
    SemesterGradeRepository semesterGradeRepository;
    AuthService authService;
    UserService userService;
    AccessSecurityService accessSecurityService;

    @Override
    public void addGrade(AddGradeDto addGrade) {
        Grade grade = new Grade();

//        checkIfSubjectExists(addGrade.getSubjectId());
//        accessSecurityService.checkHasAddAccess();
//        accessSecurityService.checkAddGradeToStudent(addGrade.getUserId());

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

    @Override
    public List<SemesterGradeDto> getSemesterGrades(long userId) {
        return semesterGradeRepository.findAllByUserId(userId).stream()
                .map(SemesterGrade::dto)
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
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> new GradeNotFoundException(gradeId));
        grade.setSubjectId(editGradeDto.getSubjectId());
        grade.setGrade(editGradeDto.getGrade());
        grade.setComment(editGradeDto.getComment());
        grade.setUserId(editGradeDto.getUserId());
        gradeRepository.save(grade);
    }
}