package com.index.service;

import com.index.dto.AddBehaviourDto;
import com.index.dto.EditBehaviourDto;
import com.index.dto.UserBehaviourDetailsDto;
import com.index.model.BehaviourName;

public interface BehaviourService {
    void addBehaviour(AddBehaviourDto addBehaviour);

    UserBehaviourDetailsDto getUserBehaviours(long userId);

    double getFinalBehaviourAverage(long userId);

    BehaviourName getFinalBehaviourName(long userId);

    void editBehaviour(EditBehaviourDto editBehaviourDto, long behaviourId);
}
