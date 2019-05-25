package com.example.aplikacjapogodowa;

public class WeatherCard {
    private String Code_image;
    private String temperature;
    private String data;
    private String pressure;
    private String wind;

    public WeatherCard(String Code_image, String temperature, String data,String pressure,String wind) {

        this.Code_image = Code_image;
        this.temperature = temperature;
        this.data = data;
        this.pressure =pressure;
        this.wind =wind;
    }

    public String getCode_image() {
        return Code_image;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getData() {
        return data;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWind() {
        return wind;
    }
}