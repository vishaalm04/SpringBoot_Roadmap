package com.example.CRUD.mapper;

import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.entity.UserEntity;
import com.example.CRUD.enumeration.UserStatus;

public class UserMapper {

    public UserDTO toDTO(UserEntity user) {
        return UserDTO.builder()
                .name()
                .build();
    }
    public static UserEntity toEntity(UserDTO user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setEmailId(user.getEmailId());
        entity.setUniqueCode(user.getUniqueCode());
        entity.setStatus(user.getStatus().equalsIgnoreCase("Active") ? UserStatus.ACTIVE : UserStatus.INACTIVE);
        entity.setCreatedBy("admin");
        entity.setUpdatedBy("admin");
        return entity;
    }
    public static void updateEntityFromDTO(UserDTO user, UserEntity entity) {
        entity.setName(user.getName());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setEmailId(user.getEmailId());
        entity.setUniqueCode(user.getUniqueCode());
        entity.setStatus(user.getStatus().equalsIgnoreCase("Active") ? UserStatus.ACTIVE : UserStatus.INACTIVE);
        entity.setUpdatedBy("admin");
    }
}
