package com.index.domain.repository;

import com.index.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

//    @Modifying
//    @Query("DELETE FROM User u WHERE u.indexNumber" = :"indexNumber")
//    void deleteById(@Param("indexNumber") Long indexNumber);
}
