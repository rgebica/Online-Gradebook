package com.index.service.serviceImpl;

import com.index.dto.*;
import com.index.exceptions.SpringGradebookException;
import com.index.model.Behaviour;
import com.index.model.Role;
import com.index.model.User;
import com.index.repository.BehaviourRepository;
import com.index.repository.SubjectRepository;
import com.index.service.BehaviourService;
import com.index.service.DateService;
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
}

