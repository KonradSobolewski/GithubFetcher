package com.githubfetcher.service.implementation;

import com.githubfetcher.entity.Role;
import com.githubfetcher.repository.RoleRepository;
import com.githubfetcher.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findAllByNameIn(Collection<String> names) {
        return !names.isEmpty() ? roleRepository.findAllIn(names) : Collections.emptySet();
    }
}
