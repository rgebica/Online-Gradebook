package com.index.repository;

import com.index.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    List<Presence> findAllByUserId(long userId);
}
