package com.inventory.service.impl;

import com.inventory.dto.ResponseUserDTO;
import com.inventory.dto.UserDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.model.User;
import com.inventory.repository.UserRepository;
import com.inventory.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        //userDTO to User
        User user = mapper.map(userDTO, User.class);
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        //saving to user
        User saveUser = userRepository.save(user);
        //user to userDTO
        UserDTO map = mapper.map(saveUser, UserDTO.class);
        return map;
    }

    @Override
    public ResponseUserDTO getUserById(int userId) {
        User byUserId = userRepository.findByUserId(userId);
        if (byUserId == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        ResponseUserDTO responseUserDTO = mapper.map(byUserId, ResponseUserDTO.class);
        return responseUserDTO;
    }

    @Override
    public UserDTO deleteUserById(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
        // Convert and return the deleted user if needed
        User deletedUser = userOptional.get();
        UserDTO deletedUserDTO = mapper.map(deletedUser, UserDTO.class);

        return deletedUserDTO;
    }

    @Override
    public List<ResponseUserDTO> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(user -> mapper.map(user, ResponseUserDTO.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ResponseUserDTO> getAllUsers() {
//        List<User> userList = userRepository.findAll();
//        return userList.stream()
//                .map(user -> mapper.map(user, ResponseUserDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public UserDTO updateUserById(int userId, UserDTO updatedUserDTO) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            // Update other fields as needed
            existingUser.setName(updatedUserDTO.getName());
            existingUser.setEmail(updatedUserDTO.getEmail());
            existingUser.setPassword(updatedUserDTO.getPassword());
            existingUser.setAddress(updatedUserDTO.getAddress());
            existingUser.setAbout(updatedUserDTO.getAbout());
            existingUser.setGender(updatedUserDTO.getGender());
            existingUser.setPhone(updatedUserDTO.getPhone());
            existingUser.setActive(updatedUserDTO.isActive());
            // Save the updated user
            User updatedUser = userRepository.save(existingUser);

            // Map and return the updated user as a UserDTO
            return mapper.map(updatedUser, UserDTO.class);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
    }
}
