package com.E_Commerce.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.E_Commerce.dto.request.LoginRequest;
import com.E_Commerce.dto.request.RegisterRequest;
import com.E_Commerce.dto.response.LoginResponse;
import com.E_Commerce.dto.response.UserResponse;
import com.E_Commerce.entity.User;
import com.E_Commerce.exception.InvalidPasswordException;
import com.E_Commerce.exception.UserNotFoundException;
import com.E_Commerce.repository.UserRepository;

@Service
public class UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    private JwtService jwtService;


    public UserService(UserRepository repository, BCryptPasswordEncoder encoder, JwtService jwtService){
        this.repository=repository;
        this.encoder= encoder;
        this.jwtService=jwtService;
    }

    public UserResponse register(RegisterRequest request){
        User user=new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        User saved=repository.save(user);
        UserResponse r=new UserResponse();
        r.setId(saved.getId());
        r.setFullName(saved.getFullName());
        r.setEmail(saved.getEmail());
        return r;
    }

    public LoginResponse login(LoginRequest request){
        User user=repository.findByEmail(request.getEmail())
            .orElseThrow(()->new UserNotFoundException("User not found"));
        if(!encoder.matches(request.getPassword(),user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }
        return new LoginResponse(jwtService.generate(user.getEmail()));
    }




}
