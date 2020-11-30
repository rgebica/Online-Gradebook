package com.index.repository;

import com.index.dto.UserDto;
import com.index.model.Class;
import com.index.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllByClassId(long classId);

    @Modifying
    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_STUDENT'")
    List<User> findAllStudents();

    @Modifying
    @Query("DELETE FROM User u WHERE u.userId IN ?1")
    void deleteUsersByIds(List<Long> userIds);
}
