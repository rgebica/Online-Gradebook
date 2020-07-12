package com.index.service;

import com.index.dto.PresenceDto;
import com.index.dto.UserDto;
import com.index.dto.UserPresenceDetailsDto;
import com.index.model.Presence;
import com.index.model.Subject;
import com.index.repository.SubjectRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PresenceService {

    SubjectRepository subjectRepository;
    GradeService gradeService;
    UserService userService;

    @Autowired
    public PresenceService(SubjectRepository subjectRepository, GradeService gradeService, UserService userService) {
        this.subjectRepository = subjectRepository;
        this.gradeService = gradeService;
        this.userService = userService;
    }

    public List<UserPresenceDetailsDto> getPresenceByUserId(long userId) {
        Map<Long, List<PresenceDto>> presenceBySubjectsId = gradeService.getPresenceByUserId(userId).stream()
                .collect(Collectors.groupingBy(PresenceDto::getSubjectId));
        Set<Long> subjectIds = presenceBySubjectsId.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<PresenceDto> presences = presenceBySubjectsId.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return UserPresenceDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), user, getPresenceCounter(presences), getAbsenceCounter(presences), getPresencePercentage(presences));
                }).collect(Collectors.toList());
    }

    private long getPresenceCounter(List<PresenceDto> presences) {
        return presences.stream()
                .filter((presence -> presence.isPresence()))
                .count();
    }

    private long getAbsenceCounter(List<PresenceDto> presences) {
        return presences.stream()
                .filter(presence -> !presence.isPresence())
                .count();
    }

    private long getPresencePercentage(List<PresenceDto> presences) {
        long presencesAndAbsences = presences.stream().
                map(PresenceDto::getPresenceId)
                .count();
        return (int) (getPresenceCounter(presences) * 100.0 / presencesAndAbsences + 0.5);
    }
}
