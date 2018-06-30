package com.skook.skook.model;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class OffersMain {

    private int code;
    private String message;
    private ArrayList<Offers> data;
    private int all;

    public OffersMain() {
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

    public ArrayList<Offers> getData() {
        return data;
    }

    public void setData(ArrayList<Offers> data) {
        this.data = data;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
