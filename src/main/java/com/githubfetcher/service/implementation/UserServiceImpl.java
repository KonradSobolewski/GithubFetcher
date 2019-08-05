package com.githubfetcher.service.implementation;

import com.githubfetcher.dto.NewUserDTO;
import com.githubfetcher.entity.Notification;
import com.githubfetcher.entity.User;
import com.githubfetcher.event.NotificationEvent;
import com.githubfetcher.exception.UserAlreadyExists;
import com.githubfetcher.exception.UserNotFound;
import com.githubfetcher.repository.UserRepository;
import com.githubfetcher.service.RoleService;
import com.githubfetcher.service.UserService;
import org.assertj.core.util.Lists;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.githubfetcher.utils.Keys.PHONE_NUMBER;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private ApplicationEventPublisher publisher;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    public void saveUser(NewUserDTO newUserDTO) throws UserAlreadyExists {

        Optional<User> foundOne = userRepository.findOneByLogin(newUserDTO.getLogin());
        if (foundOne.isPresent()) {
            throw new UserAlreadyExists(String.format("User with login: %s already exists", newUserDTO.getLogin()));
        }

        User user = User.builder()
                .login(newUserDTO.getLogin())
                .password(newUserDTO.getPassword())
                .firstName(newUserDTO.getFirstName())
                .lastName(newUserDTO.getLastName())
                .build();

        if (newUserDTO.getParams().containsKey(PHONE_NUMBER)) {
            user.addParam(PHONE_NUMBER, newUserDTO.getParams().get(PHONE_NUMBER));
        }

        user.setRoles(roleService.findAllByNameIn(Lists.newArrayList("USER")));
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOneById(Long id) {
        return userRepository.findOneById(id);
    }


    @Override
    @Transactional
    public void updateUser(NewUserDTO newUserDTO) throws UserNotFound {
        User user = userRepository.findOneByLogin(newUserDTO.getLogin())
                .orElseThrow(() -> new UserNotFound(String.format("User with login: %s not found", newUserDTO.getLogin())));

        user.setFirstName(newUserDTO.getFirstName());
        user.setLastName(newUserDTO.getLastName());
        user.setLastName(newUserDTO.getLastName());

        if (newUserDTO.getParams().containsKey(PHONE_NUMBER)) {
            user.addParam(PHONE_NUMBER, newUserDTO.getParams().get(PHONE_NUMBER));
        }
        publisher.publishEvent(NotificationEvent.builder()
                .source(this)
                .notification(Notification.builder()
                        .message(String.format("User with login %s updated", user.getLogin()))
                        .build()
                )
                .build()
        );
    }
}
