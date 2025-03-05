package com.example.CRUD.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListDTO {
    private String offset;
    private String limit;
    private String totalSize;
    private List<UserDTO> data;
}
