package com.index.repository;

import com.index.model.Behaviour;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BehaviourRepository extends JpaRepository<Behaviour, Long> {
}