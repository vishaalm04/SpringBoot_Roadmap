package com.example.CRUD.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ApiResponseDTO {

    private int code;
    private String message;
}
