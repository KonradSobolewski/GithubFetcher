package com.githubfetcher.repository;

import com.githubfetcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE id = ?1", nativeQuery = true)
    Optional<User> findOneById(Long id);

    @Query(value = "SELECT * FROM users WHERE login = ?1", nativeQuery = true)
    Optional<User> findOneByLogin(String login);
}
