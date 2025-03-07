package com.example.CRUD.controller;


import com.example.CRUD.dto.ApiResponseDTO;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.dto.UserListDTO;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    public ApiResponseDTO createUser( @Valid @RequestBody UserDTO user,
                                      @RequestHeader("CreatedBy") String createdBy)  throws DuplicateKeyException {
        return  userService.createUser(user,createdBy);
    }

    @DeleteMapping("/{id}")
    public  ApiResponseDTO deleteUser(@PathVariable Long id,
                                      @RequestHeader("UpdatedBy") String updatedBy) {
        return userService.deleteUser(id,updatedBy);
    }

    @PutMapping("/{id}")
    public ApiResponseDTO updateUser(@PathVariable Long id,
                                     @RequestBody UserDTO user,
                                     @RequestHeader("UpdatedBy") String updatedBy) throws DuplicateKeyException {
        return userService.updateUser(id, user,updatedBy);
    }

    @GetMapping("/search")
    public UserListDTO searchUsers(

            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "BOTH") String status,
            @RequestParam(required = false, defaultValue = "ASC") String sortBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10") Integer size)
    {
        return userService.searchUsers(search, status,sortBy,page,size);
    }




}
