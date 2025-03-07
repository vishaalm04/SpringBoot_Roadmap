package com.example.CRUD.validation;

import com.example.CRUD.constants.ErrorConstants;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessValidator {

    @Autowired
    private UserRepository userRepository;

    public void validateUniqueConstraints(UserDTO user) throws DuplicateKeyException {
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new DuplicateKeyException(ErrorConstants.ERROR_DUPLICATE_PHONE);
        }
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new DuplicateKeyException(ErrorConstants.ERROR_DUPLICATE_EMAIL);
        }
    }

    public void validateUniqueConstraintsForUpdate(Long id, UserDTO user) throws DuplicateKeyException {
        if (user.getPhoneNumber() != null && userRepository.existsByPhoneNumberAndIdNot(user.getPhoneNumber(), id)) {
            throw new DuplicateKeyException(ErrorConstants.ERROR_DUPLICATE_PHONE);
        }
        if (user.getEmailId() != null && userRepository.existsByEmailIdAndIdNot(user.getEmailId(), id)) {
            throw new DuplicateKeyException(ErrorConstants.ERROR_DUPLICATE_EMAIL);
        }
    }
}
