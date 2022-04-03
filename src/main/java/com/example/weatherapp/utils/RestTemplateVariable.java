package com.example.weatherapp.utils;


import org.springframework.stereotype.Component;

@Component
public class RestTemplateVariable {

    private String url = "http://api.weatherapi.com/v1";
    private String apiMethod = "/current.json";
    private String key = "891c577f7c0d470d895182657223003";
    private String param;

    public RestTemplateVariable() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return url + apiMethod + "?key=" + key + "&q=" + param;
    }
}
