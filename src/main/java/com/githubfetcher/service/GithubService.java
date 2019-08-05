package com.githubfetcher.service;

import java.util.List;
import java.util.Map;

public interface GithubService {
    List<Map<String, Object>> findAll(String owner);

    Map<String, Object> findByName(String owner, String name);
}
