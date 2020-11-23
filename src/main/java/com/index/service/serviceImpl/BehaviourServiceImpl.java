package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.model.Behaviour;
import com.index.repository.BehaviourRepository;
import com.index.service.BehaviourService;
import com.index.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Builder
public class BehaviourServiceImpl implements BehaviourService {

    BehaviourRepository behaviourRepository;
    GradeServiceImpl gradeService;
    AuthServiceImpl authService;
    UserService userService;

    @Override
    public BehaviourDto addBehaviour(AddBehaviourDto addBehaviour) {
        return behaviourRepository.save(Behaviour.createBehaviour(addBehaviour)).dto();
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
}

