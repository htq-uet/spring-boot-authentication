package com.example.repository;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("""
    SELECT u.role FROM User u
    WHERE u.username = :username
""")
    Role findRoleByUsername(String username);
}
