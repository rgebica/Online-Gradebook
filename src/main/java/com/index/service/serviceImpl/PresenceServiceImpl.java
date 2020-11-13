package com.index.service.serviceImpl;

import com.index.dto.AddPresenceDto;
import com.index.dto.PresenceDto;
import com.index.dto.UserDto;
import com.index.dto.UserPresenceDetailsDto;
import com.index.model.Presence;
import com.index.model.Subject;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import com.index.service.PresenceService;
import com.index.service.UserService;
import com.index.service.serviceImpl.GradeServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PresenceServiceImpl implements PresenceService {

    SubjectRepository subjectRepository;
    GradeServiceImpl gradeService;
    UserService userService;
    PresenceRepository presenceRepository;

    @Override
    public PresenceDto addPresenceDto(AddPresenceDto addPresence) {
        return presenceRepository.save(Presence.createPresence(addPresence)).dto();
    }

    @Override
    public List<UserPresenceDetailsDto> getPresenceByUserId(long userId) {
        Map<Long, List<PresenceDto>> presenceBySubjectsId = gradeService.getPresenceByUserId(userId).stream()
                .collect(Collectors.groupingBy(PresenceDto::getSubjectId));
        Set<Long> subjectIds = presenceBySubjectsId.keySet();
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        UserDto user = userService.getById(userId);

        return subjects.stream()
                .map(subject -> {
                    List<PresenceDto> presences = presenceBySubjectsId.getOrDefault(subject.getSubjectId(), Collections.emptyList());
                    return UserPresenceDetailsDto.from(subject.getSubjectId(), subject.getSubjectName(), presences, user, getPresenceCounter(presences), getAbsenceCounter(presences), getPresencePercentage(presences));
                }).collect(Collectors.toList());
    }

    private long getPresenceCounter(List<PresenceDto> presences) {
        return presences.stream()
                .filter((PresenceDto::isPresence))
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
