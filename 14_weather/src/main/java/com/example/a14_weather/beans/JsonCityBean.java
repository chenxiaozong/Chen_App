package com.example.a14_weather.beans;

/**
 * Created by chen on 2017/8/23.
 * 服务器返回的city 数据对应的javabean
 */

public class JsonCityBean {

    /**
     * id : 113
     * name : 南京
     */

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "JsonCityBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
