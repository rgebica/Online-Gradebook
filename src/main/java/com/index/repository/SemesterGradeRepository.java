package com.index.repository;

import com.index.model.SemesterGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SemesterGradeRepository extends JpaRepository<SemesterGrade, Long> {
    @Query("SELECT g FROM SemesterGrade g WHERE g.subjectId = ?2 and g.userId =?1")
    Collection<SemesterGrade> findAllByUserIdAndSubjectId(long userId, long subjectId);

    Collection<SemesterGrade> findAllByUserId(long userId);
}
