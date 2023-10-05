package com.inventory.controller;

import com.inventory.dto.ResponseUserDTO;
import com.inventory.dto.UserDTO;
import com.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
@PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user){
    Date date = new Date();
    user.setDate(date);
    UserDTO createUser = userService.createUser(user);
    return new ResponseEntity<UserDTO>(createUser, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@PathVariable("id") int id){
        ResponseUserDTO userById = userService.getUserById(id);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable("id") int id){
        UserDTO userById = userService.deleteUserById(id);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<ResponseUserDTO>> getAllUsers() {
        List<ResponseUserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable int userId, @RequestBody UserDTO updatedUserDTO) {
        UserDTO updatedUser = userService.updateUserById(userId, updatedUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
