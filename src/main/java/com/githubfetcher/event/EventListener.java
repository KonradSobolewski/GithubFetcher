package com.githubfetcher.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class EventListener {
    private static final Logger logger = LoggerFactory.getLogger(EventListener.class);

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notificationListener(NotificationEvent notificationEvent) {
        logger.info(notificationEvent.getNotification().getMessage());
    }
}
