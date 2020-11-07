package com.index.service;

import com.index.dto.AddBehaviourDto;
import com.index.dto.BehaviourDto;
import com.index.dto.UserBehaviourDetailsDto;

public interface BehaviourService {
    BehaviourDto addBehaviour(AddBehaviourDto addBehaviour);

    UserBehaviourDetailsDto getUserBehaviours(long userId);
}
