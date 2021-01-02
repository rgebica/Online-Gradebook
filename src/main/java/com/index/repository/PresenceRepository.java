package com.index.repository;

import com.index.model.Grade;
import com.index.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findAllByUserId(long userId);

    @Modifying
    @Query("SELECT g FROM Presence g WHERE g.subjectId = ?2 and g.userId =?1")
    Collection<Presence> findAllByUserIdAndSubjectId(long userId, long subjectId);
}
