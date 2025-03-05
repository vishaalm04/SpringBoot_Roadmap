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
                .status(user.getStatus().getStatus())
                .updatedBy(user.getUpdatedBy())
                .build();
    }
    public UserEntity toEntity(UserDTO user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setEmailId(user.getEmailId());
        entity.setCreatedBy("user");
        entity.setUpdatedBy("admin");
        return entity;
    }

    public  void updateEntityFromDTO(UserDTO user, UserEntity entity) {
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setEmailId(user.getEmailId());
        entity.setUniqueCode(user.getUniqueCode());
        entity.setStatus(user.getStatus().equalsIgnoreCase("Active") ? UserStatus.ACTIVE : UserStatus.INACTIVE);
        entity.setUpdatedBy("admin");
    }
}
