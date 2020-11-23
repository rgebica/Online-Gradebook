package com.index.repository;

import com.index.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByUserId(long userId);
    @Modifying
    @Query("DELETE FROM Grade g WHERE g.gradeId IN ?1")
    void deleteGradesByIds(List<Long> gradeIds);
}
