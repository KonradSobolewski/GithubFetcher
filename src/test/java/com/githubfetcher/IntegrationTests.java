package com.githubfetcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubfetcher.controller.UserController;
import com.githubfetcher.dto.NewUserDTO;
import com.githubfetcher.entity.User;
import com.githubfetcher.repository.UserRepository;
import com.githubfetcher.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc restMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup(){
        this.restMvc = MockMvcBuilders.standaloneSetup(userController).build();
        userRepository.save(User.builder()
                .login("test@test.test")
                .firstName("Test")
                .lastName("test")
                .password("test")
                .build()
        );
    }

    @After
    public void clean() {
        userService.removeUser("konradTestTest@test.test");
        userService.removeUser("test@test.test");
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    public void addUserTest() throws Exception {
        NewUserDTO user = new NewUserDTO();
        user.setFirstName("Konrad");
        user.setLastName("Test");
        user.setLogin("konradTestTest@test.test");
        user.setPassword("testtest");

        restMvc.perform(post("/user")
                .content(convertObjectToJsonBytes(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Optional<User> newUser = userService.findOneByLoginWithRoles("konradTestTest@test.test");
        assertTrue(newUser.isPresent());
        assertTrue(!newUser.get().getLogin().isEmpty());
    }

    @Test
    public void updateUserTest() throws Exception {
        Optional<User> user = userService.findOneByLoginWithRoles("test@test.test");

        assertTrue(user.isPresent());

        NewUserDTO userDTO = new NewUserDTO();
        userDTO.setLogin(user.get().getLogin());
        userDTO.setFirstName("After Update");

        restMvc.perform(put("/user")
                .content(convertObjectToJsonBytes(userDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        Optional<User> updated = userService.findOneByLoginWithRoles("test@test.test");
        assertTrue(updated.isPresent());
        assertEquals(updated.get().getFirstName(), "After Update");
    }

    @Test
    public void getByIdTest() throws Exception {
        Optional<User> user = userService.findOneByLoginWithRoles("test@test.test");

        assertTrue(user.isPresent());

        restMvc.perform(get("/user/" + user.get().getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo((result) -> {
                    String content = result.getResponse().getContentAsString();
                    User foundUser = objectMapper.readValue(content, new TypeReference<User>(){{}});
                    assertEquals(foundUser.getId(), user.get().getId());
                });

    }

}
