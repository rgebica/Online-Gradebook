package com.index.service;

import com.index.dto.AddPresenceDto;
import com.index.dto.UserPresenceDetailsDto;

import java.util.List;

public interface PresenceService {
    void addPresenceDto(AddPresenceDto addPresence);

    List<UserPresenceDetailsDto> getPresenceByUserId(long userId);

}
