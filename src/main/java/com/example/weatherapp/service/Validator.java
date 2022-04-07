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

    public boolean isValidName(String name, String password) {
        if (name.equals("Oybek") && password.equals("123")) {
            return true;
        }
        return false;
    }
/*        User user = userRepo.findByUsername(name);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }*/
}