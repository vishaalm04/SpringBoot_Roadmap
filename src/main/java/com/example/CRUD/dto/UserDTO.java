package com.example.CRUD.dto;

import com.example.CRUD.constants.ErrorConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;

    @NotBlank(message =  ErrorConstants.ERROR_NAME_EMPTY)
    @Pattern(regexp = "^[A-Za-z ]{3,50}$", message = ErrorConstants.ERROR_NAME_PATTERN)
    private String name;

    @NotBlank(message = ErrorConstants.ERROR_PHONE_EMPTY)
    @Pattern(regexp = "^[1-9]\\d{9}$", message = ErrorConstants.ERROR_PHONE_PATTERN)
    private String phoneNumber;

    @NotBlank(message = ErrorConstants.ERROR_EMAIL_EMPTY)
    @Email(message = ErrorConstants.ERROR_EMAIL_PATTERN)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]{2,}\\.[a-zA-Z]{2,}$", message = ErrorConstants.ERROR_EMAIL_PATTERN)

    private String emailId;

    private String uniqueCode;
    private String status;
    private String updatedBy;
    private String createdBy;

}
