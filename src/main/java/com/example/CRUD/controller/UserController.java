package com.example.CRUD.controller;


import com.example.CRUD.dto.ApiResponseDTO;
import com.example.CRUD.dto.UserDTO;
import com.example.CRUD.dto.UserListDTO;
import com.example.CRUD.exception.DuplicateKeyException;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
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
    public ApiResponseDTO createUser(@RequestBody UserDTO user) throws DuplicateKeyException {
        return  userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public  ApiResponseDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ApiResponseDTO updateUser(@PathVariable Long id, @RequestBody UserDTO user) throws DuplicateKeyException {
        return userService.updateUser(id, user);
    }

    @GetMapping("/search")
    public UserListDTO searchUsers(
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "BOTH") String status,
            @RequestParam(required = false, defaultValue = "ASC") String sortBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) Integer size)
    {

        if(size==0){
            size = Integer.MAX_VALUE;
        }
        return userService.searchUsers(search, status,sortBy,page,size);
    }




}
