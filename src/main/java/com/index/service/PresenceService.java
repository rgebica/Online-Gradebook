package com.index.service;

import com.index.dto.AddPresenceDto;
import com.index.dto.EditPresenceDto;
import com.index.dto.UserPresenceDetailsDto;

import java.util.List;

public interface PresenceService {
    void addPresenceDto(AddPresenceDto addPresence);

    List<UserPresenceDetailsDto> getPresenceByUserId(long userId);

    long getFinalPresencePercentage(long userId);

    void editPresence(EditPresenceDto editPresenceDto, long presenceId);
}
