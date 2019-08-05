package com.githubfetcher.repository;

import com.githubfetcher.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role WHERE id = ?1", nativeQuery = true)
    Optional<Role> findOneById(Long id);

    @Query(value = "SELECT * FROM role WHERE name in ?1", nativeQuery = true)
    Set<Role> findAllIn(Collection<String> roles);
}
