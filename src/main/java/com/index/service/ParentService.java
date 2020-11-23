package com.index.service;

import com.index.dto.ParentChildrenDto;

public interface ParentService {
    ParentChildrenDto getParentPersonalInformation(long userId);
}
