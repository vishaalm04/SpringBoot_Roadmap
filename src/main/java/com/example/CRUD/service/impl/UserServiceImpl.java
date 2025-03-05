package com.example.CRUD.service.impl;

import com.example.CRUD.constants.ResponseConstants;
import com.example.CRUD.dto.ApiResponseDTO;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.dto.UserListDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.exception.InvalidDataException;
import com.example.CRUD.exception.UserNotFoundException;
import com.example.CRUD.mapper.UserMapper;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponseDTO createUser(UserDTO user) throws DuplicateKeyException {

        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new InvalidDataException("Phone Number cannot be null or empty.");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be null or empty.");
        }
        if (user.getEmailId() == null || user.getEmailId().trim().isEmpty()) {
            throw new InvalidDataException("Email ID cannot be null or empty.");
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DuplicateKeyException("User with given Phone Number already exists.");
        }
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new DuplicateKeyException("User with given Email ID already exists.");
        }

        try {
            UserEntity userEntity = userMapper.toEntity(user);
            userEntity.setUniqueCode(generateUniqueCode());
            userEntity.setStatus(UserStatus.ACTIVE);
            userRepository.save(userEntity);
            return new ApiResponseDTO(HttpStatus.OK.value(), ResponseConstants.USER_CREATED);
        } catch (Exception e) {
            throw new InvalidDataException("An unexpected error occurred while creating the user.");
        }
    }

    private String generateUniqueCode() {
        long count = userRepository.count() + 1;
        return "ATS" + String.format("%03d", count);
    }

    @Override
    public ApiResponseDTO updateUser(Long id, UserDTO user) throws DuplicateKeyException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
                throw new DuplicateKeyException("User with given Phone Number already exists.");
            }
            if (userRepository.existsByEmailId(user.getEmailId())) {
                throw new DuplicateKeyException("User with given Email ID already exists.");
            }
            userMapper.updateEntityFromDTO(user, userEntity);
            userEntity.setUpdatedBy("user");
            userRepository.save(userEntity);
            return new ApiResponseDTO(HttpStatus.OK.value(), ResponseConstants.USER_UPDATED);
        } else {
            throw new UserNotFoundException("User Not Found");
        }
    }

    @Override
    public  ApiResponseDTO deleteUser(Long id) {
    UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

    userEntity.setStatus(UserStatus.INACTIVE);
    userEntity.setUpdatedBy("user");
    userRepository.save(userEntity);
    return new ApiResponseDTO(HttpStatus.OK.value(), ResponseConstants.USER_DELETED);



}
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findByStatus(UserStatus.ACTIVE)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
    @Override
    public UserDTO getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .filter(user -> user.getStatus() == UserStatus.ACTIVE) // Ensure user is active
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found or inactive"));

        return userMapper.toDTO(userEntity);
    }
    @Override
    public UserListDTO searchUsers(String searchTerm, String status, String sortBy, int page, int size) {
        Sort sort = sortBy.equalsIgnoreCase("DESC")
                ? Sort.by(Sort.Direction.DESC, "name")
                : Sort.by(Sort.Direction.ASC, "name");

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserEntity> userEntities;
        if (status.equalsIgnoreCase("ACTIVE")) {
            userEntities = userRepository.findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailIdContainingIgnoreCaseOrStatusAndPhoneNumberStartingWithOrStatusAndUniqueCodeStartingWithIgnoreCase(
                    UserStatus.ACTIVE, searchTerm,
                    UserStatus.ACTIVE, searchTerm,
                    UserStatus.ACTIVE, searchTerm,
                    UserStatus.ACTIVE, searchTerm,
                    pageable);
        } else if (status.equalsIgnoreCase("INACTIVE")) {
            userEntities = userRepository.findByStatusAndNameContainingIgnoreCaseOrStatusAndEmailIdContainingIgnoreCaseOrStatusAndPhoneNumberStartingWithOrStatusAndUniqueCodeStartingWithIgnoreCase(
                    UserStatus.INACTIVE, searchTerm,
                    UserStatus.INACTIVE, searchTerm,
                    UserStatus.INACTIVE, searchTerm,
                    UserStatus.INACTIVE, searchTerm,
                    pageable);
        } else { // BOTH (default case)
            userEntities = userRepository.findByNameContainingIgnoreCaseOrEmailIdContainingIgnoreCaseOrPhoneNumberStartingWithOrUniqueCodeStartingWithIgnoreCase(
                    searchTerm, searchTerm, searchTerm, searchTerm, pageable);
        }
        UserListDTO userListDTO = new UserListDTO();
        userListDTO.setOffset(String.valueOf(size));
        userListDTO.setLimit(String.valueOf(userEntities.getNumberOfElements()));
        userListDTO.setTotalSize(String.valueOf(userEntities.getTotalElements()));
        userListDTO.setData(userEntities.getContent().stream()
                .map(userMapper::toDTO)
                .toList());
        return userListDTO;
    }
}
