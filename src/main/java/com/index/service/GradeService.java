package com.index.service;

import com.index.dto.*;
import com.index.model.Grade;
import com.index.model.Presence;
import com.index.model.Subject;
import com.index.repository.GradeRepository;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GradeService {

    GradeRepository gradeRepository;
    SubjectRepository subjectRepository;
    PresenceRepository presenceRepository;

    public GradeService(GradeRepository gradeRepository, SubjectRepository subjectRepository, PresenceRepository presenceRepository) {
        this.gradeRepository = gradeRepository;
        this.subjectRepository = subjectRepository;
        this.presenceRepository = presenceRepository;
    }

    public GradeDto addGrade(AddGradeDto addGrade) {
        return gradeRepository.save(Grade.createGrade(addGrade)).dto();
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

}