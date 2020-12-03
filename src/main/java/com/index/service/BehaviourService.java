package com.index.service;

import com.index.dto.AddBehaviourDto;
import com.index.dto.UserBehaviourDetailsDto;

public interface BehaviourService {
    void addBehaviour(AddBehaviourDto addBehaviour);

    UserBehaviourDetailsDto getUserBehaviours(long userId);
}
