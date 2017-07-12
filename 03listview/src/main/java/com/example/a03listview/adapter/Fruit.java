package com.example.a03listview.adapter;

/**
 * Created by chen on 2017/7/4.
 */

public class Fruit {
    private  String name;
    private  int imageid;

    public Fruit() {
    }

    public Fruit(String name, int imageid) {
        this.name = name;
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }
}
