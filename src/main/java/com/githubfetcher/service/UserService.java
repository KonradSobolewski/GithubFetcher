package com.githubfetcher.service;


import com.githubfetcher.dto.NewUserDTO;
import com.githubfetcher.entity.User;
import com.githubfetcher.exception.UserAlreadyExists;
import com.githubfetcher.exception.UserNotFound;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(NewUserDTO newUserDTO) throws UserAlreadyExists;

    List<User> getAll();

    Optional<User> findOneById(Long id);

    void updateUser(NewUserDTO newUserDTO) throws UserNotFound;
}
