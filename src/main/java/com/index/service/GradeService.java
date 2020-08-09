package com.index.service;

import com.index.dto.*;
import com.index.model.Behaviour;
import com.index.model.Grade;
import com.index.model.Presence;
import com.index.model.Subject;
import com.index.repository.BehaviourRepository;
import com.index.repository.GradeRepository;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GradeService {

    GradeRepository gradeRepository;
    SubjectRepository subjectRepository;
    PresenceRepository presenceRepository;
    BehaviourRepository behaviourRepository;

    public GradeService(GradeRepository gradeRepository, SubjectRepository subjectRepository, PresenceRepository presenceRepository, BehaviourRepository behaviourRepository) {
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
        this.presenceRepository = presenceRepository;
        this.behaviourRepository = behaviourRepository;
    }

    public GradeDto addGrade(AddGradeDto addGrade) {
        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
    }

    public BehaviourDto addBehaviour(AddBehaviourDto addBehaviour) {
        return behaviourRepository.save(Behaviour.createBehaviour(addBehaviour)).dto();
    }

    public List<GradeDto> getGradesByUser(long userId) {
        return gradeRepository.findAllByUserId(userId).stream()
                .map(Grade::dto)
                .collect(Collectors.toList());
    }

    public List<SubjectDto> getSubjectsByUser(long userId) {
        return subjectRepository.findAllByUserId(userId).stream()
                .map(Subject::dto)
                .collect(Collectors.toList());
    }

    public PresenceDto addPresenceDto(AddPresenceDto addPresence) {
        return presenceRepository.save(Presence.createPresence(addPresence)).dto();
    }

    public List<PresenceDto> getPresenceByUserId(long userId) {
        return presenceRepository.findAllByUserId(userId).stream()
                .map(Presence::dto)
                .collect(Collectors.toList());
    }
}