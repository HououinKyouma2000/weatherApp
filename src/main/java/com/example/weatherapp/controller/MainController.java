package com.example.weatherapp.controller;

import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.utils.RestError;
import com.example.weatherapp.utils.RestTemplateVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/main")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final RestTemplate restTemplate;
    private final WeatherService weatherService;
    private final RestTemplateVariable restTemplateVariable;
    private String errMess = "Unknown error";

    public MainController(WeatherService weatherService, RestTemplateVariable restTemplateVariable) {
        this.weatherService = weatherService;
        this.restTemplateVariable = restTemplateVariable;
        this.restTemplate = new RestTemplate();
    }


    @RequestMapping(value = "/get-temperature", method = GET)
    @PreAuthorize("@validator.isValidName(#name,#password)")
    public ResponseEntity<RestError> getTemperature(
            @RequestParam String paramQ,
            @RequestParam  String name,@RequestParam  String password) {
        log.info("> getTemperature with Param={}", paramQ);
        try {
            if (paramQ != null) {
                restTemplateVariable.setParam(paramQ);
                    String json = restTemplate.getForObject(restTemplateVariable.toString(), String.class);
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
        }
        catch (Exception e) {
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(1, errMess));
        }
    }
}
