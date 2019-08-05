package com.githubfetcher.controller;

import com.githubfetcher.service.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value = "/git", produces = MediaType.APPLICATION_JSON_VALUE)
public class GithubController {

    private GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{owner}/all")
    public ResponseEntity getAllUsers(@PathVariable(value = "owner") String owner) {
        return ResponseEntity.ok(githubService.findAll(owner));
    }

    @GetMapping("/{owner}/{name}")
    public ResponseEntity getAllByName(@PathVariable(value = "owner") String owner, @PathVariable(value = "name") String name) {
        Map repo = githubService.findByName(owner, name);
        if (repo.isEmpty()) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Repository not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(repo);
    }
}
