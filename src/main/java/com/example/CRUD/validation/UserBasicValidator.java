package com.example.CRUD.validation;

import com.example.CRUD.constants.ErrorConstants;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBasicValidator {
    private static final List<String> INVALID_NAMES = List.of("test", "user", "admin", "administrator", "guest");

    public void validateUser(UserDTO user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new InvalidDataException(ErrorConstants.ERROR_NAME_EMPTY);
        }
        if (INVALID_NAMES.contains(user.getName().trim().toLowerCase())) {
            throw new InvalidDataException(ErrorConstants.ERROR_NAME_GENERIC);
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().trim().isEmpty()) {
            throw new InvalidDataException(ErrorConstants.ERROR_PHONE_EMPTY);
        }
        if (user.getEmailId() == null || user.getEmailId().trim().isEmpty()) {
            throw new InvalidDataException(ErrorConstants.ERROR_EMAIL_EMPTY);
        }
    }
}
