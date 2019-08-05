package com.githubfetcher.intergration;

import feign.*;

import java.util.List;
import java.util.Map;

public interface GithubClient {

    @RequestLine("GET /users/{owner}/repos")
    @Headers({"Content-Type: application/json"})
    List<Map<String, Object>> getUserRepos(@Param("owner") String owner);

    @RequestLine("GET /search/repositories?q={name}")
    @Headers({"Content-Type: application/json"})
    Response getReposByName(@Param("name") String name);

    @RequestLine("GET /search/repositories?q={name}{filters}")
    @Headers({"Content-Type: application/json"})
    Response getReposByNameAndFilters(@Param("name") String name, @Param("owner") String filters);
}
