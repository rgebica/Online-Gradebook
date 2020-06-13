package com.index.repository;

import com.index.dto.GradeDto;
import com.index.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByUserId(long userId);
}
