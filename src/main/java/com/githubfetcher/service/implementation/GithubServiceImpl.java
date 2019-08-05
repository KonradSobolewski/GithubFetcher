package com.githubfetcher.service.implementation;

import com.githubfetcher.intergration.GithubClient;
import com.githubfetcher.service.GithubService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Service
public class GithubServiceImpl implements GithubService {

    private GithubClient githubClient;

    public GithubServiceImpl(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public List<Map<String, Object>> findAll(String owner) {
        return githubClient.getUserRepos(owner);
    }

    @Override
    public Map<String, Object> findByName(String owner, String name) {
        List<Map<String, Object>> repos = githubClient.getUserRepos(owner);
        Map<String, Object> toReturn = emptyMap();
        for(Map<String, Object> repo : repos)
            if(name.equals(repo.get("name"))) {
                toReturn = repo;
                break;
            }
        return toReturn;
    }
}
