package com.skook.skook.model;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class RegionMain {

    private int code;
    private String message;
    private ArrayList<Region> data;

    public RegionMain() {
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

    public ArrayList<Region> getData() {
        return data;
    }

    public void setData(ArrayList<Region> data) {
        this.data = data;
    }
}
