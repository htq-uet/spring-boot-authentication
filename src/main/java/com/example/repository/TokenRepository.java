package com.example.repository;

import com.example.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
    SELECT t FROM Token t inner join User u on t.user.id = u.id\s
    WHERE u.id = :userId AND t.expired = false or t.revoked = false
""")
    List<Token> findAllValidTokenByUser(Integer userId);

    @Override
    Optional<Token> findById(Integer integer);

    Optional<Token> findByToken(String token);

}
