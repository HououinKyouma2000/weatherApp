package com.example.weatherapp.entity;


public class Weather {
    private String name;
    private Double tempC;

    public Weather(String name, Double tempC) {
        this.name = name;
        this.tempC = tempC;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public String getName() {
        return name;
    }



    public Double getTempC (){
        return tempC;
    }



    @Override
    public String toString() {
        return "Weather{" +
                "name='" + name + '\'' +
                ", temp_c=" + tempC +
                '}';
    }
}
