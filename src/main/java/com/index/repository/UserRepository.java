package com.index.repository;

import com.index.model.Class;
import com.index.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Collection<User> findAllByClassId(long classId);

    @Modifying
    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_STUDENT'")
    Collection<User> findAllStudents();

    @Modifying
    @Query("DELETE FROM User u WHERE u.userId IN ?1")
    void deleteUsersByIds(List<Long> userIds);

    Class findByClassId(long userId);

    @Modifying
    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_PARENT'")
    Collection<User> findAllParents();

    @Modifying
    @Query("SELECT u FROM User u WHERE u.role = 'ROLE_TEACHER'")
    Collection<User> findAllTeachers();

    @Modifying
    @Query("UPDATE User u SET u.password = ?1 where u.userId = ?2")
    void setNewPassword(String newGeneratedPassword, long userId);
}
