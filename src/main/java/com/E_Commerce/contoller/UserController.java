package com.E_Commerce.contoller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.E_Commerce.dto.request.LoginRequest;
import com.E_Commerce.dto.request.RegisterRequest;
import com.E_Commerce.dto.response.LoginResponse;
import com.E_Commerce.dto.response.UserResponse;
import com.E_Commerce.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService service;

    public UserController(UserService service){
        this.service=service;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return service.login(request);
    }
}
