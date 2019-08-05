package com.githubfetcher.event;

import com.githubfetcher.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificationEvent extends ApplicationEvent {
    private Notification notification;
    @Builder
    public NotificationEvent(Object source, Notification notification) {
        super(source);
        this.notification = notification;
    }
}
