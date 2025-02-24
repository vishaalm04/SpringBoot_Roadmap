package com.example.CRUD.controller;

import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/phone/{phoneNumber}")
    public Optional<UserDTO> getUserByPhoneNumber(@PathVariable String phoneNumber) {
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO user) throws DuplicateKeyException {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.updateUser(id, user);
    }


}
