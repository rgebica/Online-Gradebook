package com.index.service;

import com.index.dto.*;
import com.index.service.serviceImpl.AuthServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResultsService {

    PresenceService presenceService;
    ClassService classService;
    SubjectService subjectService;
    UserService userService;
    BehaviourService behaviourService;

    public UserResultsDto getUserResults(long userId) {
        UserDto user = userService.getById(userId);
        ClassDto classDto = classService.findClassByClassId(user.getClassId());

        return UserResultsDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .className(classDto.getClassName())
                .finalSubjectsAverage(subjectService.getFinalAverage(userId))
                .finalSubjectsPresence(presenceService.getFinalPresencePercentage(userId))
                .finalBehaviour(behaviourService.getFinalBehaviourAverage(userId))
                .finalBehaviourName(behaviourService.getFinalBehaviourName(userId))
                .build();
    }
}
