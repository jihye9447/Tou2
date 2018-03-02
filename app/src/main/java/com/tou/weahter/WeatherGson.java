package com.tou.weahter;

public class WeatherGson{

    public WeatherGson(Weather weather){
        setWeather(weather);
    }

    private Weather weather;
    
    public Weather getWeather(){
        return weather;
    }
    
    public void setWeather(Weather weather){
        this.weather = weather;
    }
}