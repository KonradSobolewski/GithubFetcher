package com.githubfetcher.entity;


import java.time.OffsetDateTime;

public class GitHubRepo {
    private String name;
    private Long stars;
    private String description;
    private OffsetDateTime updateDate;
    private OffsetDateTime creationDate;
    private String language;
}
