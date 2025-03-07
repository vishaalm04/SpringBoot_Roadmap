package com.example.CRUD.service.impl;

import com.example.CRUD.constants.ErrorConstants;
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
import com.example.CRUD.repository.UserSpecification;
import com.example.CRUD.service.UserService;
import com.example.CRUD.validation.UserBasicValidator;
import com.example.CRUD.validation.UserBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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


    @Autowired
    private UserBasicValidator userBasicValidator;

    @Autowired
    private UserBusinessValidator userBusinessValidator;

    @Override
    public ApiResponseDTO createUser(UserDTO user,String createdBy) throws DuplicateKeyException {

        userBasicValidator.validateUser(user);
        userBusinessValidator.validateUniqueConstraints(user);
        try {
            UserEntity userEntity = userMapper.toEntity(user);
            userEntity.setUniqueCode(generateUniqueCode());
            userEntity.setStatus(UserStatus.ACTIVE);
            userEntity.setCreatedBy(createdBy);
            userEntity.setUpdatedBy(createdBy);
            userRepository.save(userEntity);
            return new ApiResponseDTO(HttpStatus.OK.value(), ResponseConstants.USER_CREATED);
        } catch (Exception e) {
            throw new InvalidDataException(ErrorConstants.USER_CREATION_ERROR);
        }
    }

    private String generateUniqueCode() {
        long count = userRepository.count() + 1;
        return "ATS" + String.format("%03d", count);
    }

    @Override
    public ApiResponseDTO updateUser(Long id, UserDTO user,String updatedBy) throws DuplicateKeyException {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(String.format(ErrorConstants.USER_NOT_FOUND,id));
        }

        userBusinessValidator.validateUniqueConstraintsForUpdate(id, user);
        UserEntity userEntity = optionalUser.get();
        userMapper.updateEntityFromDTO(user, userEntity);
        userEntity.setUpdatedBy(updatedBy);
        userRepository.save(userEntity);

        return new ApiResponseDTO(HttpStatus.OK.value(), ResponseConstants.USER_UPDATED);
    }

    @Override
    public  ApiResponseDTO deleteUser(Long id,String updatedBy) {
    UserEntity userEntity = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(String.format(ErrorConstants.USER_NOT_FOUND, id)));

    userEntity.setStatus(UserStatus.INACTIVE);
    userEntity.setUpdatedBy(updatedBy);
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
                .orElseThrow(() -> new UserNotFoundException(String.format(ErrorConstants.USER_INACTIVE_OR_NOT_FOUND, id)));

        return userMapper.toDTO(userEntity);
    }
    @Override
    public UserListDTO searchUsers(String searchTerm, String status, String sortBy, int page, int size) {

        if (!status.equalsIgnoreCase(UserStatus.ACTIVE.getValue()) &&
                !status.equalsIgnoreCase(UserStatus.INACTIVE.getValue()) &&
                !status.equalsIgnoreCase(ResponseConstants.USER_STATUS_BOTH)) {
            throw new IllegalArgumentException(String.format(ErrorConstants.INVALID_FILTER_OPTION, status));
        }

        Sort sort = sortBy.equalsIgnoreCase(ResponseConstants.DESCENDING)
                ? Sort.by(Sort.Direction.DESC, ResponseConstants.SORT_BY_NAME)
                : Sort.by(Sort.Direction.ASC, ResponseConstants.SORT_BY_NAME);

        Pageable pageable = PageRequest.of(page, size, sort);
       /* Page<UserEntity> userEntities;
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
        }*/
        Specification<UserEntity> spec = UserSpecification.filterUsers(searchTerm, status);
        Page<UserEntity> userEntities = userRepository.findAll(spec, pageable);

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
