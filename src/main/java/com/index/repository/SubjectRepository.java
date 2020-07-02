package com.index.repository;

import com.index.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllByUserId(long userId);
}
