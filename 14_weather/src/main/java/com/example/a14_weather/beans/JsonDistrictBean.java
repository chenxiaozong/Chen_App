package com.example.a14_weather.beans;

/**
 * Created by chen on 2017/8/23.
 * 县区级对应的json解析bean
 */

public class JsonDistrictBean {


    /**
     * id : 799
     * name : 济南
     * weather_id : CN101120101
     */

    private int id;
    private String name;
    private String weather_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    @Override
    public String toString() {
        return "JsonDistrictBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weather_id='" + weather_id + '\'' +
                '}';
    }
}
