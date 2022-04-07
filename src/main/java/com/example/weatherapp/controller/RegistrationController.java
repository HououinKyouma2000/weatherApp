package com.example.weatherapp.controller;

import com.example.weatherapp.dto.User;
import com.example.weatherapp.repo.UserRepo;
import com.example.weatherapp.utils.Parser;
import com.example.weatherapp.utils.RestError;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);
    private final UserRepo userRepo;
    private String errMess = "Unknown error";

    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @RequestMapping(value = "/add-user", method = POST)
    public ResponseEntity<RestError> addUser(@RequestBody String json) {
        log.info("> addUser with json = {}", json);
        try {
            JsonObject jo = Parser.parser.parse(json).getAsJsonObject();
            String username = jo.get("username").getAsString();
            String password = jo.get("password").getAsString();
            if (username != null && password != null) {
                if (userRepo.findByUsername(username) == null) {
                    User user = new User(username, password);
                    user.setActive(true);
                    userRepo.save(user);
                    log.info("< registration");
                    return ResponseEntity.ok(new RestError(user));
                }
                errMess = "Driver is already exist";
                log.info("< registration with error = {}", errMess);
                return ResponseEntity.badRequest().body(new RestError(3, errMess));
            }
            errMess = "empty data";
            log.info("< registration with error = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(2, errMess));
        } catch (Exception e) {
            log.info("< registration with error = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(1, errMess));
        }
    }
}

