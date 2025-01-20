package com.url_shortner.bitly.controller;

import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.LoginRequest;
import com.url_shortner.bitly.request.RegisterRequest;
import com.url_shortner.bitly.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/public/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        User user=new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER_ROLE");
        return new ResponseEntity<>( userService.userRegister(user),HttpStatus.OK);
    }
    @PostMapping("/public/login")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest logInRequest){
        return new ResponseEntity<>(userService.authenticateRequest(logInRequest), HttpStatus.OK);
    }
}
