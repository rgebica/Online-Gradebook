package com.index.service.serviceImpl;

import com.index.dto.AddPresenceDto;
import com.index.dto.PresenceDto;
import com.index.dto.UserDto;
import com.index.dto.UserPresenceDetailsDto;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Presence;
import com.index.model.Role;
import com.index.model.Subject;
import com.index.model.User;
import com.index.repository.PresenceRepository;
import com.index.repository.SubjectRepository;
import com.index.service.AuthService;
import com.index.service.DateService;
import com.index.service.PresenceService;
import com.index.service.UserService;
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
    PresenceRepository presenceRepository;
    GradeServiceImpl gradeService;
    UserService userService;
    AuthService authService;

    @Override
    public void addPresenceDto(AddPresenceDto addPresence) {
        Presence presence = new Presence();

//        checkHasAddAccess();
//        checkIfSubjectExists(addPresence.getSubjectId());
//        checkAddGradeToStudent(addPresence.getUserId());

        presence.setUserId(addPresence.getUserId());
        presence.setSubjectId(addPresence.getSubjectId());
        presence.setPresence(addPresence.isPresence());
        presence.setDate(DateService.getFormattedDate());
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
    public long getFinalPresencePercentage(long userId) {
        User user = userService.findById(userId);
        List<UserPresenceDetailsDto> presences = getPresenceByUserId(user.getUserId());

        double countedPresences = presences.stream()
                .mapToDouble(UserPresenceDetailsDto::getPresencePercentage)
                .average()
                .orElse(Double.NaN);

        return (long) countedPresences;
    }
}
