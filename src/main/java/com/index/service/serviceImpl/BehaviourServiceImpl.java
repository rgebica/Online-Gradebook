package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Behaviour;
import com.index.model.BehaviourName;
import com.index.model.Role;
import com.index.model.User;
import com.index.repository.BehaviourRepository;
import com.index.repository.SubjectRepository;
import com.index.service.BehaviourService;
import com.index.service.DateService;
import com.index.service.PresenceService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Builder
public class BehaviourServiceImpl implements BehaviourService {

    BehaviourRepository behaviourRepository;
    PresenceService presenceService;
    GradeServiceImpl gradeService;
    AuthServiceImpl authService;
    UserService userService;
    SubjectRepository subjectRepository;

    @Override
    public void addBehaviour(AddBehaviourDto addBehaviour) {
        Behaviour behaviour = new Behaviour();
//        checkHasAddAccess();

        checkAddGradeToStudent(addBehaviour.getUserId());
        behaviour.setUserId(addBehaviour.getUserId());
        behaviour.setGrade(addBehaviour.getGrade());
        behaviour.setDate(DateService.getFormattedDate());
        behaviour.setAddedBy(addedBy());

        behaviourRepository.save(behaviour);
    }

    @Override
    public UserBehaviourDetailsDto getUserBehaviours(long userId) {
        List<BehaviourDto> behavioursByUserId = gradeService.getBehaviourByUser(userId);
        UserDto user = userService.getById(userId);

        return UserBehaviourDetailsDto.builder()
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .behaviours(behavioursByUserId)
                .build();
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
    public double getFinalBehaviourAverage(long userId) {
        User user = userService.findById(userId);
        List<BehaviourDto> behavioursList = behaviourRepository.findAllByUserId(user.getUserId()).stream()
                .map(Behaviour::dto)
                .collect(Collectors.toList());

        double finalAverage = behavioursList.stream()
                .mapToDouble(BehaviourDto::getGrade)
                .average()
                .orElse(Double.NaN);

        return Math.round(finalAverage * 100.0) / 100.0;
    }

    @Override
    public BehaviourName getFinalBehaviourName(long userId) {
        User user = userService.findById(userId);
        double finalBehaviourAverage = getFinalBehaviourAverage(user.getUserId());
        long finalPresencePercentage = presenceService.getFinalPresencePercentage(user.getUserId());

        if (finalBehaviourAverage <= 1.74 && finalBehaviourAverage <= 2.58 || finalPresencePercentage <= 33) {
            return BehaviourName.NAGANNE;
        }
        if (finalBehaviourAverage >= 1.75  && finalBehaviourAverage <= 2.59 && finalPresencePercentage >= 34) {
            return BehaviourName.NIEODPOWIEDNIE;
        }
        if (finalBehaviourAverage >= 2.60  && finalBehaviourAverage <= 3.59 && finalPresencePercentage >= 47 ) {
            return BehaviourName.POPRAWNE;
        }
        if (finalBehaviourAverage >= 3.60  && finalBehaviourAverage <= 4.59 && finalPresencePercentage >= 67) {
            return BehaviourName.DOBRE;
        }
        if (finalBehaviourAverage >= 4.60  && finalBehaviourAverage <= 5.29 && finalPresencePercentage >= 83) {
            return BehaviourName.BARDZO_DOBRE;
        }
        if (finalBehaviourAverage >= 5.30 && finalPresencePercentage >= 98) {
            return BehaviourName.WZOROWE;
        }
        return null;
    }
}

