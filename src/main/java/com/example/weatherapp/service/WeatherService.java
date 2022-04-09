package com.example.weatherapp.service;

import com.example.weatherapp.entity.Weather;
import com.example.weatherapp.utils.Parser;
import com.example.weatherapp.utils.RestError;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    String errMess = "Unknown error";

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    public String getTemperatureFromAPI(String paramQ) {
        log.info("> getTemperatureFromAPI  with param={}", paramQ);
        String url = "http://api.weatherapi.com/v1";
        String apiMethod = "/current.json";
        String key = "891c577f7c0d470d895182657223003";
        String fullUrl = url + apiMethod + "?key=" + key + "&q=" + paramQ;
        String json=null;
        json = restTemplate.getForObject(fullUrl, String.class);
        log.info("< getTemperatureFromAPI");
        return json;
    }

    public RestError getTemperature(String json) {
        log.info("> getTemperature  with Json={}", json);
        try {
            JsonObject jo = Parser.parser.parse(json).getAsJsonObject();
            JsonObject location = jo.get("location").getAsJsonObject();
            JsonObject current = jo.get("current").getAsJsonObject();
            String cityName = location.get("name").getAsString();
            Double tempC = current.get("temp_c").getAsDouble();
            Weather weather = new Weather(cityName, tempC);
            log.info("< getTemperature");
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
