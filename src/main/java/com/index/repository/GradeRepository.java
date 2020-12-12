package com.index.repository;

import com.index.model.Grade;
import com.index.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByUserId(long userId);

    @Modifying
    @Query("DELETE FROM Grade g WHERE g.gradeId IN ?1")
    void deleteGradesByIds(List<Long> gradeIds);

    @Modifying
    @Query("SELECT g FROM Grade g WHERE g.subjectId = ?2 and g.userId =?1")
    Collection<Grade> findAllByUserIdAndSubjectId(long userId, long subjectId);
}
