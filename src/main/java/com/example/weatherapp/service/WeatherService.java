package com.example.weatherapp.service;

import com.example.weatherapp.entity.Weather;
import com.example.weatherapp.utils.Parser;
import com.example.weatherapp.utils.RestError;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    String errMess = "Unknown error";


    public RestError getTemperature(String json) {
        log.info("> getTemperature  with Json={}", json);
        try {
            JsonObject jo = Parser.parser.parse(json).getAsJsonObject();
            JsonObject location = jo.get("location").getAsJsonObject();
            JsonObject current = jo.get("current").getAsJsonObject();
            String cityName = location.get("name").getAsString();
            Double tempC = current.get("temp_c").getAsDouble();
            Weather weather = new Weather(cityName, tempC);
            log.info("< getTemperature" );
            return new RestError(weather);
        } catch (NullPointerException e) {
            errMess = "NullPointerException";
            log.error("< getTemperature with error = {}", errMess);
            return new RestError(6, errMess);
        } catch (Exception e) {
            log.error("< getTemperature with error = {}", errMess);
            return new RestError(5, errMess);
        }
    }
}
