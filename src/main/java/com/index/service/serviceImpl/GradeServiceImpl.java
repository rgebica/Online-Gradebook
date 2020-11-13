package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.*;
import com.index.repository.BehaviourRepository;
import com.index.repository.GradeRepository;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import com.index.service.GradeService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    UserService userService;

    @Override
    public GradeDto addGrade(AddGradeDto addGrade) {
        checkIfSubjectExists(addGrade.getSubjectId());
//        checkHasAddAccess(addGrade.getUserId());
        User user = userService.findById(addGrade.getUserId());
        return gradeRepository.save(Grade.createGrade(addGrade, user)).dto();
    }

    @Override
    public List<GradeDto> getGradesByUser(long userId) {
        return gradeRepository.findAllByUser_UserId(userId).stream()
                .map(Grade::dto)
                .collect(Collectors.toList());
    }

    public List<SubjectDto> getSubjectsByUser(long userId) {
        return subjectRepository.findAllByPupils_UserId(userId).stream()
                .map(Subject::dto)
                .collect(Collectors.toList());
    }

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
}