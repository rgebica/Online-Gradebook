package com.index.service;

import com.index.dto.CreateBehaviourResponse;
import com.index.dto.ParentChildrenDto;

public interface ParentService {
    ParentChildrenDto getParentPersonalInformation(long userId);

    void createResponseToBehaviour(CreateBehaviourResponse createBehaviourResponse);
}
