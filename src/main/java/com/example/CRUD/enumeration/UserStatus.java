package com.example.CRUD.enumeration;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("User is Active","ACTIVE"),
    INACTIVE("User is InActive","INACTIVE");

    private final String description;
    private final String value;

    UserStatus(String description,String value){
        this.description=description;
        this.value=value;
   }



}
