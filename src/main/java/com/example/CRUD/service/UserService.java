package com.example.CRUD.service;
import com.example.CRUD.dto.ApiResponseDTO;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.dto.UserListDTO;
import com.example.CRUD.exception.DuplicateKeyException;
import java.util.List;


public interface UserService {
    ApiResponseDTO createUser(UserDTO user,String createdBy) throws DuplicateKeyException;

    ApiResponseDTO updateUser(Long id, UserDTO user,String updatedBy) throws DuplicateKeyException;

    ApiResponseDTO deleteUser(Long id,String updatedBy);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);


    UserListDTO searchUsers(String searchTerm, String status, String sortBy, int page, int size);
}
