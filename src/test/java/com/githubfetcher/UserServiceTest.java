package com.githubfetcher;

import com.githubfetcher.entity.Role;
import com.githubfetcher.entity.User;
import com.githubfetcher.repository.UserRepository;
import com.githubfetcher.service.implementation.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    private User getUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));

        return User.builder()
                .login("test@test.test")
                .roles(roles)
                .build();
    }

    @Test
    public void getUser_unitTest_positive(){
        User mock = getUser();
        when(userRepository.findOneByLogin(any())).thenReturn(Optional.of(mock));

        Optional<User> user = userService.findOneByLoginWithRoles("test@test.test");

        assertTrue(user.isPresent());
    }
}
