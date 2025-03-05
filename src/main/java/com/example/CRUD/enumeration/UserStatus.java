package com.example.CRUD.enumeration;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("User is Active"),
    INACTIVE("User is InActive");

    private final String status;

    UserStatus(String status){
        this.status=status;
   }


}
