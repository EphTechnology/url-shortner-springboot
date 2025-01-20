package com.url_shortner.bitly.service;

import com.url_shortner.bitly.Repository.UserRepo;
import com.url_shortner.bitly.jwtservice.JwtResponse;
import com.url_shortner.bitly.jwtservice.JwtUtils;
import com.url_shortner.bitly.model.User;
import com.url_shortner.bitly.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private JwtUtils jwtUtils;
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    public User userRegister(User user){
        return userRepo.save(user);
    }

    public JwtResponse authenticateRequest(LoginRequest loginRequest) {
        Authentication authToken=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        UserDetailImp userDetail= (UserDetailImp) authToken.getPrincipal();
        String jwt=jwtUtils.generateJwtToken(userDetail);
        return new JwtResponse(jwt);
    }
}
