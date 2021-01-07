package com.index.repository;

import com.index.model.SemesterGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface SemesterGradeRepository extends JpaRepository<SemesterGrade, Long> {
//    @Query("SELECT g.subjectAverage FROM SemesterGrade g WHERE g.userId = ?2 and g.subjectId =?1")
//    Double findAverageByUserIdAndSubjectId(long userId, long subjectId);

    @Query("SELECT g FROM SemesterGrade g WHERE g.subjectId = ?2 and g.userId =?1")
    Collection<SemesterGrade> findAllByUserIdAndSubjectId(long userId, long subjectId);
}
