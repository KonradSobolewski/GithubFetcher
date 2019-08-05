package com.githubfetcher.service.implementation;

import com.githubfetcher.entity.UserHistory;
import com.githubfetcher.repository.UserHistoryRepository;
import com.githubfetcher.service.UserHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;

    public UserHistoryServiceImpl(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUserHistory(UserHistory userHistory) {
        userHistoryRepository.save(userHistory);
    }
}
