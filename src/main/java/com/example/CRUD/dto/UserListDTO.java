package com.example.CRUD.dto;

import java.util.List;
import lombok.*;


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
