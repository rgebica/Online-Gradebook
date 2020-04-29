package com.index.domain.repository;

import com.index.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Modifying
//    @Query("DELETE FROM User u WHERE u.indexNumber" = :"indexNumber")
//    void deleteById(@Param("indexNumber") Long indexNumber);
}
