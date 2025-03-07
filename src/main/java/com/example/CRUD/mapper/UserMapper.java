package com.example.CRUD.mapper;

import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(UserEntity user) {
        return UserDTO.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .emailId(user.getEmailId())
                .uniqueCode(user.getUniqueCode())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus().getValue())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .build();
    }
    public UserEntity toEntity(UserDTO user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setEmailId(user.getEmailId());
        entity.setCreatedBy(user.getCreatedBy());
        entity.setUpdatedBy(user.getCreatedBy());
        return entity;
    }

    public void updateEntityFromDTO(UserDTO user, UserEntity entity) {
        if (user.getName() != null) {
            entity.setName(user.getName());
        }
        if (user.getPhoneNumber() != null) {
            entity.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getEmailId() != null) {
            entity.setEmailId(user.getEmailId());
        }
        if (user.getUniqueCode() != null) {
            entity.setUniqueCode(user.getUniqueCode());
        }
        if (user.getStatus() != null) {
            entity.setStatus(user.getStatus().equalsIgnoreCase(UserStatus.ACTIVE.getValue())
                    ? UserStatus.ACTIVE
                    : UserStatus.INACTIVE);
        }
        entity.setUpdatedBy(user.getUpdatedBy());
    }

}
