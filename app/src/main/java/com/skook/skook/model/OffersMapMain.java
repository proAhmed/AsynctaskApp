package com.skook.skook.model;

import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class OffersMapMain {

    private int code;
    private String message;
    private ArrayList<OffersMap> data;

    public OffersMapMain() {
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

    public ArrayList<OffersMap> getData() {
        return data;
    }

    public void setData(ArrayList<OffersMap> data) {
        this.data = data;
    }


}
