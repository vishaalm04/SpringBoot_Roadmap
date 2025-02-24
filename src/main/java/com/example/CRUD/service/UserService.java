package com.example.CRUD.service;

import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.exception.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO user) throws DuplicateKeyException;

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUser(Long id);

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long id);

    Optional<UserDTO> getUserByPhoneNumber(String phoneNumber);
}
