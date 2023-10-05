package com.inventory.service;


import com.inventory.dto.ResponseUserDTO;
import com.inventory.dto.UserDTO;

import java.util.List;

public interface UserService {
    public UserDTO createUser(UserDTO userDTO);
    public ResponseUserDTO getUserById(int userId);
    public UserDTO deleteUserById(int userId);
    public List<ResponseUserDTO> getAllUsers();
    public UserDTO updateUserById(int userId, UserDTO updatedUserDTO);


}
