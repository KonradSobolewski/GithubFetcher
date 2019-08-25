package com.githubfetcher.repository;

import com.githubfetcher.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

    @Query(value = "DELETE FROM user_history where user_id = ?1", nativeQuery = true)
    void deleteByUserId(Long id);
}
