package com.example.a14_weather.beans;

/**
 * Created by chen on 2017/8/23.
 */

public class JsonProvinceBean {


    /**
     * id : 1
     * name : 北京
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
        return "JsonProvinceBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
