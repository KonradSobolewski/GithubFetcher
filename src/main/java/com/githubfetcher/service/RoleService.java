package com.githubfetcher.service;

import com.githubfetcher.entity.Role;

import java.util.Collection;
import java.util.Set;

public interface RoleService {
    Set<Role> findAllByNameIn(Collection<String> names);
}
