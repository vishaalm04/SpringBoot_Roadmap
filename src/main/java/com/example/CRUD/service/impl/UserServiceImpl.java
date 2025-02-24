package com.example.CRUD.service.impl;

import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.mapper.UserMapper;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUser(UserDTO user) throws DuplicateKeyException {
        try {
            UserEntity userEntity = UserMapper.toEntity(user);
            UserEntity savedUser = userRepository.save(userEntity);
            return UserMapper.toDTO(savedUser);
        } catch (Exception e) {
            throw new DuplicateKeyException("User with given Email, Phone Number, or Unique Code already exists.");
        }
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            UserMapper.updateEntityFromDTO(user, userEntity);
            UserEntity updatedUser = userRepository.save(userEntity);
            return UserMapper.toDTO(updatedUser);
        } else {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber).map(UserMapper::toDTO);
    }
}
