package com.example.weatherapp.controller;

import com.example.weatherapp.dto.User;
import com.example.weatherapp.repo.UserRepo;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.utils.Parser;
import com.example.weatherapp.utils.RestError;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/main")
public class WeatherControlle {
    private static final Logger log = LoggerFactory.getLogger(WeatherControlle.class);
    private final WeatherService weatherService;
    private final UserRepo userRepo;
    private String errMess = "Unknown error";

    public WeatherControlle(WeatherService weatherService,UserRepo userRepo) {
        this.weatherService = weatherService;
        this.userRepo = userRepo;
    }

    @RequestMapping(value = "/add-user", method = GET)
    public ResponseEntity<RestError> addUser(/*@RequestBody String json*/
    @RequestParam String username, @RequestParam String password) {
        log.info("> addUser with json = {}", username);
       /* JsonObject jo = Parser.parser.parse(json).getAsJsonObject();
        String username = null;
        String password = null;
        try {
            username = jo.get("username").getAsString();
            password = jo.get("password").getAsString();
        } catch (NullPointerException e) {
            errMess = "NullPointerException";
            log.info("< registration with error = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(1, errMess));
        }*/
        if (username != null && password != null) {
            User user = userRepo.findByUsername(username);
            if (user == null) {
                user = new User(username, password);
                userRepo.save(user);
                log.info("< registration");
                return ResponseEntity.ok(new RestError(user));
            }
            errMess = "User is already exist";
            log.info("< registration with error = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(3, errMess));
        }
        errMess = "empty data";
        log.info("< registration with error = {}", errMess);
        return ResponseEntity.badRequest().body(new RestError(2, errMess));
    }

    @RequestMapping(value = "/get-temperature", method = GET)
    @PreAuthorize("@validator.isValidName(#username,#password)")
    public ResponseEntity<RestError> getTemperature(
            @RequestParam String paramQ
            , @RequestParam String username, @RequestParam String password) {
        log.info("> getTemperature with Param={}", paramQ);
        try {
            if (paramQ != null) {
                String json = weatherService.getTemperatureFromAPI(paramQ);
                if (json != null) {
                    log.info("< getTemperature");
                    return ResponseEntity.ok(weatherService.getTemperature(json));
                }
                errMess = "rest template is null";
                log.error("< getTemperature = {}", errMess);
                return ResponseEntity.badRequest().body(new RestError(4, errMess));
            }
            errMess = "ParamQ is null";
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(2, errMess));
        } catch (HttpClientErrorException e) {
            errMess = "HttpClientErrorException";
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(3, errMess));
        } catch (Exception e) {
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(1, errMess));
        }
    }
}
