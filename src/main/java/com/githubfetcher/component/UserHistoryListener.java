package com.githubfetcher.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubfetcher.entity.User;
import com.githubfetcher.entity.UserHistory;
import com.githubfetcher.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserHistoryListener {

    static private UserHistoryService userHistoryService;

    static private ObjectMapper objectMapper;

    @Autowired
    public void init(UserHistoryService userHistoryService, ObjectMapper objectMapper) {
        UserHistoryListener.userHistoryService = userHistoryService;
        UserHistoryListener.objectMapper = objectMapper;
    }

    @PostPersist
    @PreUpdate
    public void postUpdate(User user){
        if(user != null) {
            HashMap<String, Object> after = objectMapper.convertValue(user, new TypeReference<Map<String, Object>>(){{}});
            userHistoryService.saveUserHistory(UserHistory.builder()
                    .user_id(user.getId())
                    .after(after)
                    .date(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                    .change_by(-1L)
                    .build()
            );

        }

    }

}
