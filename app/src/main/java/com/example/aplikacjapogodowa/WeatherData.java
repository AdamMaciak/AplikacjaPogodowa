package com.example.aplikacjapogodowa;

public class WeatherData {
    private String temp;
    private String pressure;
    private String weather_data;
    private String weather_description;
    private String humidity;
    private String wind_direction;
    private String wind_speed;
    private String city_name;
    private String code_icon;

    public WeatherData(){}

    public WeatherData(String temp, String pressure, String weather_data, String weather_description, String humidity, String wind_direction, String wind_speed, String city_name, String code_icon) {
        this.temp = temp;
        this.pressure = pressure;
        this.weather_data = weather_data;
        this.weather_description = weather_description;
        this.humidity = humidity;
        this.wind_direction = wind_direction;
        this.wind_speed = wind_speed;
        this.city_name = city_name;
        this.code_icon = code_icon;
    }

    public String getCode_icon() {
        return code_icon;
    }

    public void setCode_icon(String code_icon) {
        this.code_icon = code_icon;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWeather_data() {
        return weather_data;
    }

    public void setWeather_data(String weather_data) {
        this.weather_data = weather_data;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
    //private String day_name;
}
