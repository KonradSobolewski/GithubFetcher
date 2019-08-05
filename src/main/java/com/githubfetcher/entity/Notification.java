package com.githubfetcher.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Notification {
    private String message;
    private String phoneNumber;
    private String url;
}
