package com.example.weatherapp.controller;

import com.example.weatherapp.entity.Weather;
import com.example.weatherapp.utils.Parser;
import com.example.weatherapp.utils.RestError;
import com.example.weatherapp.utils.RestTemplateVariable;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/main-controller")
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final RestTemplate restTemplate;
    private final RestTemplateVariable restTemplateVariable;
    private String errMess = "Unknown error";

    public MainController(RestTemplateVariable restTemplateVariable) {
        this.restTemplateVariable = restTemplateVariable;
        this.restTemplate = new RestTemplate();
    }


    @RequestMapping(value = "/get-temperature", method = GET)
    public ResponseEntity<RestError> getTemperature(@RequestParam String paramQ) {
        log.info("> getTemperature with Param={}", paramQ);
        try {
            if (paramQ != null) {
                restTemplateVariable.setParam(paramQ);
                try {
                    String json = restTemplate.getForObject(restTemplateVariable.toString(), String.class);
                    if (json != null) {
                        JsonObject jo = Parser.parser.parse(json).getAsJsonObject();
                        JsonObject location = jo.get("location").getAsJsonObject();
                        JsonObject current = jo.get("current").getAsJsonObject();
                        Weather weather = new Weather(location.get("name").getAsString(), current.get("temp_c").getAsDouble());
                        log.info("< getTemperature");
                        return ResponseEntity.ok(new RestError(weather));
                    }
                    errMess = "rest template is null";
                    log.error("< getTemperature = {}", errMess);
                    return ResponseEntity.badRequest().body(new RestError(5, errMess));
                } catch (NullPointerException e) {
                    errMess = "Parser/NullPointer exception";
                    log.error("< getTemperature = {}", errMess);
                    return ResponseEntity.badRequest().body(new RestError(4, errMess));
                } catch (HttpClientErrorException e) {
                    errMess = "HttpClientErrorException";
                    log.error("< getTemperature = {}", errMess);
                    return ResponseEntity.badRequest().body(new RestError(3, errMess));
                }
            }
            errMess = "ParamQ is null";
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(2, errMess));
        } catch (Exception e) {
            log.error("< getTemperature = {}", errMess);
            return ResponseEntity.badRequest().body(new RestError(1, errMess));
        }
    }
}
