package com.url_shortner.bitly.service;

import com.url_shortner.bitly.Repository.UserRepo;
import com.url_shortner.bitly.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    public User userRegister(User user){
        return userRepo.save(user);
    }
}
