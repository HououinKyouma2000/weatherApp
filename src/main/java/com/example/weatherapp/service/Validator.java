package com.example.weatherapp.service;

import com.example.weatherapp.dto.User;
import com.example.weatherapp.repo.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private final UserRepo userRepo;

    public Validator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean isValidName(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}