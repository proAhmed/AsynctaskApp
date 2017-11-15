package com.skook.skook.model;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class CityMain {

    private int code;
    private String message;
    private ArrayList<City> data;

    public CityMain() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<City> getData() {
        return data;
    }

    public void setData(ArrayList<City> data) {
        this.data = data;
    }
}
