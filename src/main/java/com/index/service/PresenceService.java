package com.index.service;

import com.index.dto.*;

import java.util.List;

public interface PresenceService {
    void addPresenceDto(AddPresenceDto addPresence);

    List<UserPresenceDetailsDto> getPresenceByUserId(long userId);

    long getFinalPresencePercentage(long userId);

    void editPresence(EditPresenceDto editPresenceDto, long presenceId);

    PresenceDto getPresenceById(long presenceId);

    List<UserPresencesBySubjectDto> getUserPresencesBySubjects(long subjectId);
}
