package com.inventory.controller;

import com.inventory.dto.JWTRequest;
import com.inventory.dto.JWTResponse;
import com.inventory.dto.UserDTO;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.security.JWTHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager manager;
    private UserDetailsService userDetailsService;
    private JWTHelper helper;
    private ModelMapper model;

    @Autowired
    public AuthController(AuthenticationManager manager, UserDetailsService userDetailsService, JWTHelper helper, ModelMapper model) {
        this.manager = manager;
        this.userDetailsService = userDetailsService;
        this.helper = helper;
        this.model = model;
    }

    @PostMapping("/login")
public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
autheticateUser(request.getUsername(),request.getPassword());
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
   String token= helper.generateToken(userDetails);
   JWTResponse response = new JWTResponse();
   response.setToken(token);
   response.setUser(model.map(userDetails, UserDTO.class));
return new ResponseEntity<JWTResponse>(response, HttpStatus.ACCEPTED);
}

public void autheticateUser(String username, String password){
try {
    manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
}
catch (BadCredentialsException e){
throw new ResourceNotFoundException("invalid username or password");
}
catch (DisabledException e){
    throw  new ResourceNotFoundException("user is not active");
}
}
}
