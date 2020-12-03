package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.*;
import com.index.repository.*;
import com.index.service.AuthService;
import com.index.service.GradeService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
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
    UserRepository userRepository;

    @Override
    public GradeDto addGrade(AddGradeDto addGrade) {
        checkIfSubjectExists(addGrade.getSubjectId());
//        checkHasAddAccess(addGrade.getUserId());
//        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
//        User user = userService.findById(addGrade.getUserId());

        addGrade.setUserId(addGrade.getUserId());
        addGrade.setSubjectId(addGrade.getSubjectId());
        addGrade.setGrade(addGrade.getGrade());
        addGrade.setGradeWeight(addGrade.getGradeWeight());
        addGrade.setComment(addGrade.getComment());
        addGrade.setAddedBy(addedBy());

        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
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

    private void checkHasAddAccess(long userId) {
        UserDto user = userService.getById(userId);
        if (!user.getRole().equals(Role.ROLE_TEACHER)) {
            throw new SpringGradebookException("Has no add access");
        }
    }

    private String addedBy() {
        String addedByFirstName = authService.getCurrentUser().getFirstName();
        String addedByLastName = authService.getCurrentUser().getLastName();
        return addedByFirstName + " " + addedByLastName;
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
}