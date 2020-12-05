package com.index.repository;

import com.index.model.Behaviour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface BehaviourRepository extends JpaRepository<Behaviour, Long> {
    List<Behaviour> findAllByUserId(long userId);
    Behaviour findById(long behaviourId);
}