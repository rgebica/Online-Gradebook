package com.index.service;

import com.index.dto.*;
import com.index.model.Behaviour;
import com.index.repository.BehaviourRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class BehaviourService {

    BehaviourRepository behaviourRepository;
    GradeService gradeService;
    UserService userService;

    public BehaviourService(BehaviourRepository behaviourRepository, GradeService gradeService, UserService userService) {
        this.behaviourRepository = behaviourRepository;
        this.gradeService = gradeService;
        this.userService = userService;
    }

    public BehaviourDto addBehaviour(AddBehaviourDto addBehaviour) {
        return behaviourRepository.save(Behaviour.createBehaviour(addBehaviour)).dto();
    }

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
}

