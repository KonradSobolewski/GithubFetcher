package com.githubfetcher.controller;

import com.githubfetcher.dto.NewUserDTO;
import com.githubfetcher.exception.UserAlreadyExists;
import com.githubfetcher.exception.UserNotFound;
import com.githubfetcher.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
        try {
            userService.saveUser(newUserDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserAlreadyExists userAlreadyExists) {
            return new ResponseEntity<>(Collections.singletonMap("message", userAlreadyExists.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") Long id) {
        return userService.findOneById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody NewUserDTO newUserDTO) {
        try {
            userService.updateUser(newUserDTO);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (UserNotFound userNotFound) {
            return new ResponseEntity<>(userNotFound.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
