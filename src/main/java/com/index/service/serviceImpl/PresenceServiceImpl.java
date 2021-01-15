package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exception.PresenceNotFoundException;
import com.index.model.*;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import com.index.service.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PresenceServiceImpl implements PresenceService {

    SubjectRepository subjectRepository;
    SubjectService subjectService;
    PresenceRepository presenceRepository;
    GradeServiceImpl gradeService;
    UserService userService;
    AuthService authService;
    AccessSecurityService accessSecurityService;

    @Override
    public void addPresenceDto(AddPresenceDto addPresence) {
        Presence presence = new Presence();

//        accessSecurityService.checkHasAddAccess();
//        accessSecurityService.checkIfSubjectExists(addPresence.getSubjectId());
//        accessSecurityService.checkAddGradeToStudent(addPresence.getUserId());

        presence.setUserId(addPresence.getUserId());
        presence.setSubjectId(addPresence.getSubjectId());
        presence.setPresence(addPresence.isPresence());
        presence.setDate(DateService.getFormattedDate());
        presence.setStatus(addPresence.getStatus());
        presence.setAddedBy(addedBy());

        presenceRepository.save(presence);
    }

    @Override
    public List<UserPresenceDetailsDto> getPresenceByUserId(long userId) {
        Map<Long, List<PresenceDto>> presenceBySubjectsId = gradeService.getPresenceByUserId(userId)
                .stream()
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

    long addedBy() {
        return authService.getCurrentUser().getUserId();
    }

    @Override
    public long getFinalPresencePercentage(long userId) {
        User user = userService.findById(userId);
        List<UserPresenceDetailsDto> presences = getPresenceByUserId(user.getUserId());

        double countedPresences = presences.stream()
                .mapToDouble(UserPresenceDetailsDto::getPresencePercentage)
                .average()
                .orElse(Double.NaN);

        return (long) countedPresences;
    }

    @Override
    public void editPresence(EditPresenceDto editPresenceDto, long presenceId) {
        Presence presence = presenceRepository.findById(presenceId).
                orElseThrow(() -> new PresenceNotFoundException(presenceId));
        presence.setPresence(editPresenceDto.isPresence());
        presence.setStatus(editPresenceDto.getStatus());
        presenceRepository.save(presence);
    }

    @Override
    public PresenceDto getPresenceById(long presenceId) {
        return presenceRepository.findById(presenceId)
                .map(Presence::dto).orElseThrow(() -> new PresenceNotFoundException(presenceId));
    }

    @Override
    public List<UserPresencesBySubjectDto> getUserPresencesBySubjects(long subjectId) {
        Subject subject = subjectService.findById(subjectId);

        List<UserDto> users = subject.getUsers().stream()
                .filter(user -> user.getRole().equals(Role.ROLE_STUDENT))
                .map(User::dto)
                .collect(Collectors.toList());

        return users.stream()
                .map(user -> {
                    List<PresenceDto> presenceDtos = presenceRepository.findAllByUserIdAndSubjectId(user.getUserId(), subject.getSubjectId())
                            .stream()
                            .map(Presence::dto)
                            .collect(Collectors.toList());

                    return UserPresencesBySubjectDto.builder()
                            .subjectName(subject.getSubjectName())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .presences(presenceDtos)
                            .presenceCounter(getPresenceCounter(presenceDtos))
                            .absenceCounter(getAbsenceCounter(presenceDtos))
                            .presencePercentage(getPresencePercentage(presenceDtos))
                            .build();
                }).collect(Collectors.toList());
    }
}
