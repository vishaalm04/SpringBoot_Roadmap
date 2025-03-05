package com.example.CRUD.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String name;
    private String phoneNumber;
    private String emailId;
    private String uniqueCode;
    private String status;
    private String updatedBy;

}
